package com.jd.dogapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.api.ApiServiceInterceptor
import com.jd.dogapp.auth.LoginActivity
import com.jd.dogapp.databinding.ActivityMainBinding
import com.jd.dogapp.dogdetails.DogDetailsComposeActivity
import com.jd.dogapp.doglist.DogListActivity
import com.jd.dogapp.machinelearning.Classifier
import com.jd.dogapp.machinelearning.DogRecognition
import com.jd.dogapp.models.User
import com.jd.dogapp.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // abrir camara
                setupCamera()
            } else {
                Toast.makeText(this, "acepta pemisos", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var classifier: Classifier
    private var isCameraReady = false
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedinUser(this)
        if(user == null)
        {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        else
        {
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

        /*binding.takePhoto.setOnClickListener {
            if(isCameraReady)
                takePicture()
        }*/

        viewModel.status.observe(this)
        {
                status ->

            when(status)
            {
                is ApiResponseStatus.Error -> {
                    Toast.makeText(this, status.msg, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
                is ApiResponseStatus.Loading -> binding.progressBar.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.dog.observe(this)
        {
            dog ->
            if(dog != null)
            {
                val intent = Intent(this, DogDetailsComposeActivity::class.java)
                intent.putExtra(DogDetailsComposeActivity.MOST_PROBABLE_DOGS_IDS, viewModel.probableDogIds)
                intent.putExtra(DogDetailsComposeActivity.DOG_KEY, dog)
                intent.putExtra(DogDetailsComposeActivity.IS_RECOGNITION_KEY, true)
                startActivity(intent)
            }
        }

        viewModel.dogRecognition.observe(this)
        {
            enableTakePhotoButton(it)
        }

        requestCameraPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::cameraExecutor.isInitialized)
            cameraExecutor.shutdown()
    }

    private fun requestCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // puede abrir camara
                    setupCamera()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                //showInContextUI(...)
                    AlertDialog.Builder(this).setTitle("Acepta el permiso plz")
                        .setMessage("Acepta ombe ._.").setPositiveButton("Si") { _, _ ->
                            requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA)
                        }
                        .setNegativeButton("NO") {
                                _, _ ->
                        }.show()
            }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA)
                }
            }
        }
        else
        {
            //open camarrra :v
            setupCamera()
        }
    }



    private fun setupCamera()
    {
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation).build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }

    private fun startCamera()
    {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()
            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->

                viewModel.recognizeImage(imageProxy)
            }


            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture
                , imageAnalysis)

        }, ContextCompat.getMainExecutor(this))
    }

    private fun enableTakePhotoButton(dogRecognition: DogRecognition)
    {
        if(dogRecognition.confidence > 70.0)
        {
            binding.takePhoto.alpha = 1f
            binding.takePhoto.setOnClickListener {
                viewModel.getRecognizedDog(dogRecognition.id)
            }
        }
        else
        {
            binding.takePhoto.alpha = 0.2f
            binding.takePhoto.setOnClickListener { null }
        }
    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}
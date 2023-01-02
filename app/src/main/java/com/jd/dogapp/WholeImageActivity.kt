package com.jd.dogapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.jd.dogapp.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {

    companion object
    {
        const val PHOTO_KEY_URI = "photo_uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_KEY_URI)?: ""
        val uri = Uri.parse(photoUri)


        if(uri == null )
        {
            Toast.makeText(this, "ERROR AL TRAER IMAGEN", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        else
        {
            binding.pictureView.load(File(uri.path!!))
        }

    }
}
package com.jd.dogapp.dogdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.jd.dogapp.models.Dog
import com.jd.dogapp.R
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.databinding.ActivityDogDetailsBinding

class DogDetailsActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false)?: false
        if(dog == null)
        {
            Toast.makeText(this, R.string.dog_index_format, Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        else
        {
            binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
            binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
            binding.dog = dog
            binding.dogImage.load(dog.imageUrl)

            /*viewModel.status.observe(this) {
                    status ->

                when(status)
                {
                    is ApiResponseStatus.Error -> {
                        Toast.makeText(this, status.msg, Toast.LENGTH_SHORT).show()
                        binding.loadingWheel.visibility = View.GONE
                    }
                    is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                    is ApiResponseStatus.Success -> {
                        binding.loadingWheel.visibility = View.GONE
                        finish()
                    }
                }

            }*/

            binding.closeButton.setOnClickListener {
                if(isRecognition)
                {
                    viewModel.addDogToUser()
                }
                else
                {
                    finish()
                }

            }
        }
    }
}
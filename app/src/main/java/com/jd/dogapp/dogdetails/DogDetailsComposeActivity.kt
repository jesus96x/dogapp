package com.jd.dogapp.dogdetails

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import coil.annotation.ExperimentalCoilApi
import com.jd.dogapp.R
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.dogdetails.ui.theme.DogappTheme
import com.jd.dogapp.models.Dog
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalCoilApi::class)
@AndroidEntryPoint
class DogDetailsComposeActivity : ComponentActivity() {

    companion object {
        const val DOG_KEY = "dog"
        const val MOST_PROBABLE_DOGS_IDS = "most_probable_dogs_ids"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    //private val viewModel: DogDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val probableDogsIds = intent?.extras?.getStringArrayList(MOST_PROBABLE_DOGS_IDS) ?: mutableListOf()
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false)?: false
        if(dog == null)
        {
            Toast.makeText(this, R.string.dog_index_format, Toast.LENGTH_SHORT).show()
            finish()
            return
        }*/

        setContent {
            //val status = viewModel.status
            /*if(status.value is ApiResponseStatus.Success)
                finish()
            else*/

            DogappTheme {
                DogDetailScreen(finishActivity = { finish() })
            }


        }
    }

    /*private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }*/

    /*private fun onButtonClicked(isRecognition: Boolean, dogId: Long)
    {
        if(isRecognition)
            viewModel.addDogToUser(dogId)
        else
            finish()
    }*/

}
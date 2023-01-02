package com.jd.dogapp

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.doglist.DogRepository
import com.jd.dogapp.doglist.DogTasks
import com.jd.dogapp.machinelearning.Classifier
import com.jd.dogapp.machinelearning.ClassifierRepository
import com.jd.dogapp.machinelearning.ClassifierTasks
import com.jd.dogapp.machinelearning.DogRecognition
import com.jd.dogapp.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val dogRepository: DogTasks,
    private val classifierRepository: ClassifierTasks
): ViewModel()
{
    //private lateinit var classifier: Classifier
    //private lateinit var classifierRepository: ClassifierRepository

    private val _dog = MutableLiveData<Dog>()

    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()

    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()

    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    val probableDogIds = ArrayList<String>()

    fun recognizeImage(imageProxy: ImageProxy)
    {
        viewModelScope.launch {
            val dogRecognitionList = classifierRepository.recognizeImage(imageProxy)
            updateDogRecognition(dogRecognitionList)
            updateProbableDogIds(dogRecognitionList)
            imageProxy.close()
        }
    }

    private fun updateProbableDogIds(dogRecognitionList: List<DogRecognition>) {
        probableDogIds.clear()
        if(dogRecognitionList.size >= 5)
        {
            val recognitionListId = dogRecognitionList.subList(1, 4).map {
                it.id
            }

            probableDogIds.addAll(recognitionListId)
        }
    }

    private fun updateDogRecognition(dogRecognitionList: List<DogRecognition>) {
        _dogRecognition.value = dogRecognitionList.first()
    }

    fun getRecognizedDog(mlDogId: String)
    {
        viewModelScope.launch {
            handleResponseStatus(dogRepository.getRecognizedDog(mlDogId))
        }
    }

    private fun handleResponseStatus(status: ApiResponseStatus<Dog>) {
        if(status is ApiResponseStatus.Success)
        {
            _dog.value = status.data!!
        }

        _status.value = status
    }
}
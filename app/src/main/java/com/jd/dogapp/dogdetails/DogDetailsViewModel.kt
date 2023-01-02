package com.jd.dogapp.dogdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.doglist.DogTasks
import com.jd.dogapp.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class DogDetailsViewModel @Inject constructor(
    private val dogRepo: DogTasks, savedStateHandle: SavedStateHandle
): ViewModel()
{
    var dog: MutableState<Dog?> = mutableStateOf(savedStateHandle.get(DogDetailsComposeActivity.DOG_KEY))
        private set

    private var probableDogsIds = mutableStateOf(savedStateHandle.get<ArrayList<String>>(
        DogDetailsComposeActivity.MOST_PROBABLE_DOGS_IDS) ?: arrayListOf())

    var isRecognition = mutableStateOf(savedStateHandle.get<Boolean>(
        DogDetailsComposeActivity.IS_RECOGNITION_KEY))
        private set

    //var status = mutableStateOf<ApiResponseStatus<Any>?>(ApiResponseStatus.Loading())
    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    //private val _status = MutableLiveData<ApiResponseStatus<Any>>()

    /*val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/

    private val _probableDogList = MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList: StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun getProbableDogs()
    {
        _probableDogList.value.clear()

        viewModelScope.launch {
            dogRepo.getProbableDogs(probableDogsIds.value)
                /*.catch {
                    exception ->
                    if(exception is UnknownHostException)
                    {
                        status.value = ApiResponseStatus.Error("error descnocido")
                    }
                }*/
                .collect { apiResponseStatus ->
                if(apiResponseStatus is ApiResponseStatus.Success)
                {
                    //_probableDogList.value.add(apiResponseStatus.data)
                    val probableDogMutableList = _probableDogList.value.toMutableList()
                    probableDogMutableList.add(apiResponseStatus.data)
                    _probableDogList.value = probableDogMutableList
                }
            }
        }
    }

    fun updateDog(newDog: Dog)
    {
        dog.value = newDog
    }

    fun addDogToUser()
    {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepo.addDogToUser(dog.value!!.id))
        }
    }

    private fun handleAddDogToUserResponseStatus(Pstatus: ApiResponseStatus<Any>)
    {
        status.value = Pstatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}
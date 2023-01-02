package com.jd.dogapp.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.dogapp.models.Dog
import com.jd.dogapp.api.ApiResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val dogRepo: DogTasks
): ViewModel() {

    //private val dogRepo = DogRepository()
    var dogList = mutableStateOf<List<Dog>>(listOf())
    private set

    /*val dogList: LiveData<List<Dog>>
        get() = _dogList*/

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
            private set

    /*val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/

    init {
        //getUserDogs()
        getDogsCollection()
    }

    private fun getDogsCollection()
    {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepo.getDogsCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(pStatus: ApiResponseStatus<List<Dog>>) {
        if(pStatus is ApiResponseStatus.Success)
        {
            dogList.value = pStatus.data!!
        }

        status.value = pStatus as ApiResponseStatus<Any>
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}
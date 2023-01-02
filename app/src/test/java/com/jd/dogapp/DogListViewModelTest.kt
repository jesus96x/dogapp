package com.jd.dogapp

import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.doglist.DogListViewModel
import com.jd.dogapp.doglist.DogTasks
import com.jd.dogapp.models.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class DogListViewModelTest
{
    @ExperimentalCoroutinesApi
    @get:Rule
    var dogappCoroutineRule = DogappCoroutineRule()

    @Test
    fun downloadDogListStatusesCorrect()
    {
        class fakeDogRepo : DogTasks {
            override suspend fun getDogsCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(1, 1, "", "", "", "", "",
                            "", "", "", "", inCollection = false),
                        /*Dog(2, 2, "", "", "", "", "",
                            "", "", "", "", inCollection = false)*/
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getRecognizedDog(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(1, 1, "", "", "", "", "",
                    "", "", "", "", inCollection = false)
                )
            }

        }
        val viewModel = DogListViewModel(dogRepo = fakeDogRepo())

        assertEquals(2, viewModel.dogList.value.size)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }
}
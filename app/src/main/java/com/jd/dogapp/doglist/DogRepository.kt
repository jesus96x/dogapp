package com.jd.dogapp.doglist

import com.jd.dogapp.models.Dog
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.api.ApiService
import com.jd.dogapp.api.dto.AddDogToUserDTO
import com.jd.dogapp.api.dto.DogDTOMapper
import com.jd.dogapp.api.makeNetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DogTasks
{
    suspend fun getDogsCollection(): ApiResponseStatus<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>
    suspend fun getRecognizedDog(mlDogId: String): ApiResponseStatus<Dog>
    suspend fun getProbableDogs(probableDogsIds: ArrayList<String>): Flow<ApiResponseStatus<Dog>>
}

class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : DogTasks
{

    override suspend fun getDogsCollection(): ApiResponseStatus<List<Dog>>
    {
        return withContext(dispatcher)
        {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val userDogsListResponse = userDogsListDeferred.await()
            val allDogsListResponse = allDogsListDeferred.await()

            if(allDogsListResponse is ApiResponseStatus.Error)
            {
                allDogsListResponse
            }
            else if(userDogsListResponse is ApiResponseStatus.Error)
            {
                userDogsListResponse
            }
            else if(userDogsListResponse is ApiResponseStatus.Success
                && allDogsListResponse is ApiResponseStatus.Success)
            {
                ApiResponseStatus.Success(getCollectionList(allDogsListResponse.data,
                    userDogsListResponse.data))
            }
            else
                ApiResponseStatus.Error("error en getDogsCollection")
        }
    }

    private fun getCollectionList(allDogsList: List<Dog>, userDogsList: List<Dog>): List<Dog>
    {
        val dogs = allDogsList.map {
            if(userDogsList.contains(it))
            {
                it
            }
            else
            {
                Dog(it.id, it.index, "", "", "", "", "",
                    "", "", "", "", inCollection = false)
            }
        }.sorted()
        return dogs
    }


    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>>
    {
        return makeNetworkCall {
            val dogList = apiService.getAllDogs()
            val dogDTOList = dogList.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListtoDogDomainList(dogDTOList)
        }
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = apiService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.msg)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>>
    {
        return makeNetworkCall {
            val dogList = apiService.getUserDogs()
            val dogDTOList = dogList.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListtoDogDomainList(dogDTOList)
        }
    }

    override suspend fun getRecognizedDog(mlDogId: String): ApiResponseStatus<Dog>
    {
        return makeNetworkCall {
            val response = apiService.getRecognizedDog(mlDogId)

            if (!response.isSuccess)
            {
                throw Exception(response.message)
            }
            else
            {
                val dogDTOMapper = DogDTOMapper()
                dogDTOMapper.fromDogDTOtoDogDomain(response.data.dog)
            }
        }

    }

    override suspend fun getProbableDogs(probableDogsIds: ArrayList<String>)
    : Flow<ApiResponseStatus<Dog>> = flow {

        //val dogDTOMapper = DogDTOMapper()

        for(x in probableDogsIds)
        {
            val dog = getRecognizedDog(x)
            emit(dog)
            /*val response = apiService.getRecognizedDog(x)

            if(response.isSuccess)
            {
                emit(ApiResponseStatus.Success(dogDTOMapper.fromDogDTOtoDogDomain(response.data.dog)))
            }
            else
            {
                emit(ApiResponseStatus.Error("Hubo error :c"))
            }*/
        }
    }.flowOn(dispatcher)



}
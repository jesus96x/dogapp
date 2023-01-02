package com.jd.dogapp

import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.api.ApiService
import com.jd.dogapp.api.dto.AddDogToUserDTO
import com.jd.dogapp.api.dto.DogDTO
import com.jd.dogapp.api.dto.LoginDTO
import com.jd.dogapp.api.dto.SignUpDTO
import com.jd.dogapp.api.responses.*
import com.jd.dogapp.doglist.DogRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Test
import java.net.UnknownHostException

class DogRepositoryTest
{
    @Test
    fun testGetAllDogsError(): Unit = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): SignUpApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): SignUpApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(19, 2, "Charmeleon", "", "", "", "",
                                "", "", "", "")
                        )
                    )
                )
            }

            override suspend fun getRecognizedDog(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepo = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val res = dogRepo.getDogsCollection()
        assert(res is ApiResponseStatus.Error)
        assertEquals("hola", (res as ApiResponseStatus.Error).msg)

    }

     @Test
     fun testGetDogCollectionSuccess(): Unit = runBlocking {
         class FakeApiService : ApiService {
             override suspend fun getAllDogs(): DogListApiResponse {
                 return DogListApiResponse(
                     message = "",
                     isSuccess = true,
                     data = DogListResponse(
                         dogs = listOf(
                             DogDTO(1, 1, "Wartortle", "", "", "", "",
                                 "", "", "", ""),
                             DogDTO(19, 2, "Charmeleon", "", "", "", "",
                                 "", "", "", "")
                         )
                     )
                 )
             }

             override suspend fun signUp(signUpDTO: SignUpDTO): SignUpApiResponse {
                 TODO("Not yet implemented")
             }

             override suspend fun login(loginDTO: LoginDTO): SignUpApiResponse {
                 TODO("Not yet implemented")
             }

             override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                 TODO("Not yet implemented")
             }

             override suspend fun getUserDogs(): DogListApiResponse {
                 return DogListApiResponse(
                     message = "",
                     isSuccess = true,
                     data = DogListResponse(
                         dogs = listOf(
                             DogDTO(19, 2, "Charmeleon", "", "", "", "",
                                 "", "", "", "")
                         )
                     )
                 )
             }

             override suspend fun getRecognizedDog(mlId: String): DogApiResponse {
                 TODO("Not yet implemented")
             }

         }
         val dogRepo = DogRepository(
             apiService = FakeApiService(),
             dispatcher = TestCoroutineDispatcher()
         )

         val res = dogRepo.getDogsCollection()

         assert(res is ApiResponseStatus.Success)
         val dogCollection = (res as ApiResponseStatus.Success).data
         assertEquals(2, dogCollection.size)
         assertEquals("Charmeleon", dogCollection[1].name)
         assertEquals("", dogCollection[0].name)
     }

    @Test
    fun getDogByMLSuccess() = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): SignUpApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): SignUpApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getRecognizedDog(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        DogDTO(19, 2, "Charmeleon", "", "", "", "",
                            "", "", "", "")
                    )
                )
            }
        }
        val dogRepo = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val res = dogRepo.getRecognizedDog("holis :3")
        assert(res is ApiResponseStatus.Success)
        assertEquals(19, (res as ApiResponseStatus.Success).data.id)
    }
}
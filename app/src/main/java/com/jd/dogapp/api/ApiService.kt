package com.jd.dogapp.api

import com.jd.dogapp.api.dto.AddDogToUserDTO
import com.jd.dogapp.api.dto.LoginDTO
import com.jd.dogapp.api.dto.SignUpDTO
import com.jd.dogapp.api.responses.DefaultResponse
import com.jd.dogapp.api.responses.DogApiResponse
import com.jd.dogapp.api.responses.DogListApiResponse
import com.jd.dogapp.api.responses.SignUpApiResponse
import retrofit2.http.*

/*private val okHttpClient = OkHttpClient.Builder().addInterceptor(ApiServiceInterceptor).build()
private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create()).build()*/

interface ApiService
{
    @GET("dogs")
    suspend fun getAllDogs(): DogListApiResponse

    @POST("sign_up")
    suspend fun signUp(@Body signUpDTO: SignUpDTO): SignUpApiResponse

    @POST("sign_in")
    suspend fun login(@Body loginDTO: LoginDTO): SignUpApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST("add_dog_to_user")
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET("get_user_dogs")
    suspend fun getUserDogs(): DogListApiResponse

    @GET("find_dog_by_ml_id")
    suspend fun getRecognizedDog(@Query("ml_id") mlId: String): DogApiResponse
}

/*object DogsApi
{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}*/
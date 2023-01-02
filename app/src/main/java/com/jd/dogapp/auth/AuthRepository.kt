package com.jd.dogapp.auth

import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.api.ApiService
import com.jd.dogapp.api.dto.LoginDTO
import com.jd.dogapp.api.dto.SignUpDTO
import com.jd.dogapp.api.dto.UserDTOMapper
import com.jd.dogapp.api.makeNetworkCall
import com.jd.dogapp.models.User
import java.lang.Exception
import javax.inject.Inject

interface AuthTasks
{
    suspend fun signUp(email: String, password: String, confirmPassword: String)
            : ApiResponseStatus<User>
    suspend fun login(email: String, password: String): ApiResponseStatus<User>

}

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
): AuthTasks
{
    override suspend fun signUp(email: String, password: String, confirmPassword: String)
    : ApiResponseStatus<User>
    {
        return makeNetworkCall {
            val signUpDTO = SignUpDTO(email, password, confirmPassword)
            val signUpResponse = apiService.signUp(signUpDTO)
            val userDTO = signUpResponse.data.user

            if(!signUpResponse.isSuccess)
            {
                throw Exception(signUpResponse.message)
            }

            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOtoUserDomain(userDTO)
            //dogDTOMapper.fromDogDTOListtoDogDomainList(dogDTOList)
        }
    }

    override suspend fun login(email: String, password: String): ApiResponseStatus<User>
    {
        return makeNetworkCall {
            val loginDTO = LoginDTO(email, password)
            val loginResponse = apiService.login(loginDTO)
            val userDTO = loginResponse.data.user

            if(!loginResponse.isSuccess)
            {
                throw Exception(loginResponse.message)
            }
            else
            {
                val userDTOMapper = UserDTOMapper()
                userDTOMapper.fromUserDTOtoUserDomain(userDTO)
            }
        }
    }
}
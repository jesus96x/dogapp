package com.jd.dogapp.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthTasks
) : ViewModel()
{

    var user = mutableStateOf<User?>(null)
        private set

    var emailError = mutableStateOf<String?>(null)
        private set
    var passwordError = mutableStateOf<String?>(null)
        private set
    var confirmPasswordError = mutableStateOf<String?>(null)
        private set

    /*val user: LiveData<User>
        get() = _user*/

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    /*val status: LiveData<ApiResponseStatus<User>>
        get() = _status*/


    fun signUp(email: String, password: String, confirmPassword: String)
    {
        if(email.isEmpty())
            emailError.value = "Email esta en blanco papu"
        else if(password.isEmpty())
            passwordError.value = "La contraseña no puede estar vacia papu"
        else if(confirmPassword.isEmpty())
            confirmPasswordError.value = "La confirmacion no puede estar vacia papu"
        else if(password != confirmPassword)
        {
            confirmPasswordError.value = "Contraseñas no son iguales"
            passwordError.value = "Contraseñas no son iguales"
        }
        else
        {
            status.value = ApiResponseStatus.Loading()
            viewModelScope.launch {
                handleResponseStatus(authRepo.signUp(email, password, confirmPassword))
            }
        }
    }

    fun resetErrors()
    {
        emailError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
    }

    fun login(email: String, password: String)
    {
        status.value = ApiResponseStatus.Loading()
        viewModelScope.launch {
            handleResponseStatus(authRepo.login(email, password))
        }
    }

    private fun handleResponseStatus(pstatus: ApiResponseStatus<User>) {
        if(pstatus is ApiResponseStatus.Success)
        {
            user.value = pstatus.data
        }

        status.value = pstatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}
// test1234@mimail.com test1234
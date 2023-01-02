package com.jd.dogapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(call: suspend () -> T): ApiResponseStatus<T>
{
    return withContext(Dispatchers.IO) {
        try
        {
            ApiResponseStatus.Success(call())
        }
        catch (e: UnknownHostException)
        {
            ApiResponseStatus.Error("No hay conexion a internet")
        }
        catch(e: HttpException)
        {
            val mensaje = when(e.code())
            {
                401 -> "Error al registrarse"
                else -> "Error generico"
            }
            ApiResponseStatus.Error(mensaje)
        }
        catch (e: Exception)
        {
            val mensaje = when(e.message)
            {
                "sign_up_error" -> "Error al registrarse"
                "sign_in_error" -> "Error al loggearse"
                "user_already_exists" -> "Usuario ya existe"
                else -> "Error desconocido"
            }
            ApiResponseStatus.Error(mensaje)
        }
    }
}
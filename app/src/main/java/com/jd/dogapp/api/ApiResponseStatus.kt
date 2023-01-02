package com.jd.dogapp.api

sealed class ApiResponseStatus<T> {
    class Success<T>(val data: T) : ApiResponseStatus<T>()
    class Error<T>(val msg: String) : ApiResponseStatus<T>()
    class Loading<T>() : ApiResponseStatus<T>()
}
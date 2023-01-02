package com.jd.dogapp.api

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

object ApiServiceInterceptor : Interceptor {

    const val NEEDS_AUTH_HEADER_KEY = "needs_authentication"

    private var sessionToken: String? = null

    fun setSessionToken(sessionToken: String)
    {
        this.sessionToken = sessionToken
    }

    override fun intercept(chain: Interceptor.Chain): Response
    {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        if(request.header(NEEDS_AUTH_HEADER_KEY) != null)
        {
            //REQUIERE CREDENCIALES
            if(sessionToken == null)
            {
                throw RuntimeException("REQUIERE AUTENTICACION PARA REALIZAR ACCION")
            }
            else
            {
                requestBuilder.addHeader("AUTH_TOKEN", sessionToken!!)
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}
package com.jd.dogapp.dependencyinjection

import com.jd.dogapp.BASE_URL
import com.jd.dogapp.api.ApiService
import com.jd.dogapp.api.ApiServiceInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule
{
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit
    {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient
    {
        return OkHttpClient.Builder().addInterceptor(ApiServiceInterceptor).build()
    }


}
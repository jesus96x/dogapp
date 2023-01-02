package com.jd.dogapp.dependencyinjection

import com.jd.dogapp.auth.AuthRepository
import com.jd.dogapp.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthTasksModule
{
    @Binds
    abstract fun bindAuthTasks(
        authRepository: AuthRepository
    ): AuthTasks

}
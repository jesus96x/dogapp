package com.jd.dogapp.dependencyinjection

import com.jd.dogapp.doglist.DogRepository
import com.jd.dogapp.doglist.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class DogTasksModule
{
    @Binds
    abstract fun bindDogTasks(dogRepository: DogRepository): DogTasks
}
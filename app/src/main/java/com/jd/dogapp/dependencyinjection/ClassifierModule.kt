package com.jd.dogapp.dependencyinjection

import com.jd.dogapp.machinelearning.ClassifierRepository
import com.jd.dogapp.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ClassifierModule
{
    @Binds
    abstract fun bindClassifierTasks(
        classifierRepository: ClassifierRepository
    ): ClassifierTasks
}
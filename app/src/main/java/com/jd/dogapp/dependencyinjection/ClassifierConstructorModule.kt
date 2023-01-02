package com.jd.dogapp.dependencyinjection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer

@Module
@InstallIn(SingletonComponent::class)
object ClassifierConstructorModule
{
    @Provides
    fun providesClassifierModel(@ApplicationContext context: Context): MappedByteBuffer
    {
        return FileUtil.loadMappedFile(context, "model.tflite")
    }

    @Provides
    fun providesClassifierLabels(@ApplicationContext context: Context): List<String>
    {
        return FileUtil.loadLabels(context, "labels.txt")
    }
}
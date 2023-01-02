package com.jd.dogapp

import androidx.camera.core.ImageProxy
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.dependencyinjection.ClassifierModule
import com.jd.dogapp.dependencyinjection.DogTasksModule
import com.jd.dogapp.doglist.DogTasks
import com.jd.dogapp.machinelearning.ClassifierTasks
import com.jd.dogapp.machinelearning.DogRecognition
import com.jd.dogapp.models.Dog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DogTasksModule::class, ClassifierModule::class)
@HiltAndroidTest
class MainActivityTest
{
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidTest()

    @get:Rule(order = 1)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    class FakeDogRepository @Inject constructor() : DogTasks
    {
        override suspend fun getDogsCollection(): ApiResponseStatus<List<Dog>> {
            return ApiResponseStatus.Loading()
        }

        override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecognizedDog(mlDogId: String): ApiResponseStatus<Dog> {
            TODO("Not yet implemented")
        }

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DogTasksModule
    {
        @Binds
        abstract fun bindDogTasks(fakeDogRepository: FakeDogRepository): DogTasks
    }

    class FakeClassifierRepository @Inject constructor(): ClassifierTasks
    {
        override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
            TODO("Not yet implemented")
        }

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class ClassifierModule
    {
        @Binds
        abstract fun bindClassifierTasks(
            fakeClassifierRepository: FakeClassifierRepository
        ): ClassifierTasks
    }

    @Test
    fun showAllFabs()
    {
        onView(withId(R.id.take_photo)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.settings_fab)).check(matches(isDisplayed()))
    }
}
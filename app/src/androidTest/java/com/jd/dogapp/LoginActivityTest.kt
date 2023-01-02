package com.jd.dogapp

import android.Manifest
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.GrantPermissionRule
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.auth.AuthTasks
import com.jd.dogapp.auth.LoginActivity
import com.jd.dogapp.dependencyinjection.AuthTasksModule
import com.jd.dogapp.models.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(AuthTasksModule::class)
@HiltAndroidTest
class LoginActivityTest
{

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidTest()

    @get:Rule(order = 1)
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    class FakeAuthRepo @Inject constructor(): AuthTasks{
        override suspend fun signUp(
            email: String,
            password: String,
            confirmPassword: String
        ): ApiResponseStatus<User> {
            TODO("Not yet implemented")
        }

        override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
            return ApiResponseStatus.Success(User(1L, "hackaprende@gmail.com", "efjbsdkñf2342"))
        }

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class AuthTasksTestModule
    {
        @Binds
        abstract fun bindAuthTasks(
            fakeAuthRepository: FakeAuthRepo
        ): AuthTasks

    }

    @Test
    fun mainActivityOpensAfterUserLogin()
    {
        val context = composeTestRule.activity

        composeTestRule.onNodeWithText("login").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field").performTextInput("hackaprende@gmail.com")
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field").performTextInput("test1234")
        composeTestRule.onNodeWithText("login").performClick()

        onView(withId(R.id.take_photo)).check(matches(isDisplayed()))
    }
}


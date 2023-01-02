package com.jd.dogapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.auth.AuthScreen
import com.jd.dogapp.auth.AuthTasks
import com.jd.dogapp.auth.AuthViewModel
import com.jd.dogapp.models.User
import org.junit.Rule
import org.junit.Test

class AuthScreenTest
{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTappingRegisterButtonOpenSignUpScreen()
    {
        class FakeAuthRepo : AuthTasks{
            override suspend fun signUp(
                email: String,
                password: String,
                confirmPassword: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }
        val viewModel = AuthViewModel(FakeAuthRepo())

        composeTestRule.setContent {
            AuthScreen(onUserLoggedIn = { }, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTag = "register-button").performClick()
        composeTestRule.onNodeWithTag(testTag = "reg-button").assertIsDisplayed()

    }

    @Test
    fun testEmailErrorShowsIfTappingLoginButtonAndNotEmail()
    {
        class FakeAuthRepo : AuthTasks{
            override suspend fun signUp(
                email: String,
                password: String,
                confirmPassword: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }
        val viewModel = AuthViewModel(FakeAuthRepo())

        composeTestRule.setContent {
            AuthScreen(onUserLoggedIn = { }, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field-error").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field").performTextInput("hackaprende@gmail.com")
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field-error").assertIsDisplayed()
        //composeTestRule.onNodeWithTag(testTag = "register-button").performClick()
        //composeTestRule.onNodeWithTag(testTag = "reg-button").assertIsDisplayed()

    }
}
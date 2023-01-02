package com.jd.dogapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.doglist.DogListScreen
import com.jd.dogapp.doglist.DogListViewModel
import com.jd.dogapp.doglist.DogTasks
import com.jd.dogapp.models.Dog
import org.junit.Rule
import org.junit.Test

class DogListScreenTest
{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun progressBarShowsWhenLoadingState()
    {
        class FakeDogRepository : DogTasks
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
        val viewModel = DogListViewModel(
            FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = {

                },
                onDogClicked = {

                },
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithTag("loading_wheel").assertIsDisplayed()

    }

    @Test
    fun errorDialogShowIfErrorGettingDogs()
    {
        class FakeDogRepository : DogTasks
        {
            override suspend fun getDogsCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(msg = "hubo error")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getRecognizedDog(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }
        val viewModel = DogListViewModel(
            FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = {

                },
                onDogClicked = {

                },
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithTag("error_dialog").assertIsDisplayed()
    }

    @Test
    fun dogListShowsIfSuccessGettingDogs()
    {
        class FakeDogRepository : DogTasks
        {
            override suspend fun getDogsCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(1, 1, "Juan", "", "", "", "",
                            "", "", "", "", inCollection = true),
                        Dog(2, 24, "Pedro", "", "", "", "",
                            "", "", "", "", inCollection = false)
                    ),
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getRecognizedDog(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }
        val viewModel = DogListViewModel(
            FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = {

                },
                onDogClicked = {

                },
                viewModel = viewModel
            )
        }

        //composeTestRule.onRoot(useUnmergedTree = true).printToLog("manzana")
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-Juan").assertIsDisplayed()
        composeTestRule.onNodeWithText("24").assertIsDisplayed()
        //composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-Pedro").assertIsDisplayed()

    }
}
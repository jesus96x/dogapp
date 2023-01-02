package com.jd.dogapp.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.composables.ErrorDialog
import com.jd.dogapp.composables.LoadingWheel
import com.jd.dogapp.models.User

@Composable
fun AuthScreen(/*status: ApiResponseStatus<User>?, onLoginButtonClick: (String, String) -> Unit,*/
               //onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
               /*onErrorDialogDismiss: () -> Unit,*/ viewModel: AuthViewModel = hiltViewModel(),
               onUserLoggedIn: (User) -> Unit)
{
    val user = viewModel.user
    val userValue = user.value

    if(userValue != null)
    {
        //User.setLoggedinUser(this, userValue)
        onUserLoggedIn(userValue)
    }

    val navController = rememberNavController()
    val status = viewModel.status.value

    AutoNavHost(navController,
        onLoginButtonClick = {email, password -> viewModel.login(email, password)},
        onSignUpButtonClick = {email, password,
                               confirmPassword -> viewModel.signUp(email, password, confirmPassword)},
        viewModel)

    if(status is ApiResponseStatus.Loading)
    {
        LoadingWheel()
    }
    else if(status is ApiResponseStatus.Error)
    {
        ErrorDialog(status as ApiResponseStatus.Error<Any>, { viewModel.resetApiResponseStatus() })
    }

}

@Composable
fun AutoNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    viewModel: AuthViewModel)
{
    NavHost(
        navController = navController,
        startDestination = AuthNavDestinations.LoginScreenDestination,
    ) {
        composable(route = AuthNavDestinations.LoginScreenDestination)
        {
            LoginScreen(
                onLoginButtonClick,
                { navController.navigate(route = AuthNavDestinations.SignUpScreenDestination) },
                viewModel
            )
        }
        composable(route = AuthNavDestinations.SignUpScreenDestination)
        {
            SignUpScreen(onSignUpButtonClick, { navController.navigateUp() }, viewModel)
        }
    }
}

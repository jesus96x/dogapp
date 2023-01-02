package com.jd.dogapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.jd.dogapp.MainActivity
import com.jd.dogapp.R
import com.jd.dogapp.dogdetails.ui.theme.DogappTheme
import com.jd.dogapp.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() /*, LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions*/ {

    //private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            /*val user = viewModel.user
            val userValue = user.value

            if(userValue != null)
            {
                User.setLoggedinUser(this, userValue)
                startMainActivity()
            }
            else*/

                //val status = viewModel.status
            DogappTheme {
                AuthScreen(
                    //onErrorDialogDismiss = { resetApiResponseStatus() },
                    /*onSignUpButtonClick = { email, password, passwordConfirmation ->
                        viewModel.signUp(email, password, passwordConfirmation)
                    },*/
                    //onLoginButtonClick = { email, password -> viewModel.login(email, password)},
                    //status = status.value,
                    //viewModel = viewModel,
                    onUserLoggedIn = ::startMainActivity
                )
            }

        }
        /*val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) {
                status ->

            when (status) {
                is ApiResponseStatus.Error -> {
                    showErrorDialog(status.msg)
                    binding.progressCircular.visibility = View.GONE
                }
                is ApiResponseStatus.Loading -> binding.progressCircular.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.progressCircular.visibility = View.GONE
            }
        }

        viewModel.user.observe(this) {
            user -> if(user != null)
            {
                User.setLoggedinUser(this, user)
                startMainActivity()
            }
        }*/
    }

    /*private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }*/

    private fun startMainActivity(userValue: User)
    {
        User.setLoggedinUser(this, userValue)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /*override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidated(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(email: String, password: String, confirmPassword: String) {
        viewModel.signUp(email, password, confirmPassword)
    }*/

    /*private fun showErrorDialog(messageId: String) {
        AlertDialog.Builder(this).setTitle("error").setMessage(messageId)
            .setPositiveButton("si") { _, _ -> /* Cerrar dialogo */ }
            .create().show()
    }*/
}
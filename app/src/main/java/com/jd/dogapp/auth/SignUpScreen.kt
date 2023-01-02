package com.jd.dogapp.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jd.dogapp.R
import com.jd.dogapp.composables.AuthField
import com.jd.dogapp.composables.NavigationIcon

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    onNavigationIconClick: () -> Unit, viewModel: AuthViewModel)
{
    Scaffold(
        topBar = { SignUpScreenToolbar(onNavigationIconClick) }
    ) {
        Content(onSignUpButtonClick, viewModel, resetFieldErrors = {viewModel.resetErrors()})
    }
}

@Composable
private fun Content(
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    viewModel: AuthViewModel, resetFieldErrors: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            label = "Email",
            modifier = Modifier
                .fillMaxWidth(),
            email = email.value,
            onTextChanged =
            {
                email.value = it
                resetFieldErrors()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,),
            errorMessage = viewModel.emailError.value
        )

        AuthField(
            label = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email = password.value,
            onTextChanged =
            {
                password.value = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,),
            errorMessage = viewModel.passwordError.value
        )

        AuthField(
            label = "Confirma la contraseña",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email = confirmPassword.value,
            onTextChanged =
            {
                confirmPassword.value = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,),
            errorMessage = viewModel.confirmPasswordError.value
        )

        Button(modifier = Modifier
            .fillMaxWidth().semantics { testTag = "reg-button" }
            .padding(top = 16.dp),
            onClick = { onSignUpButtonClick(email.value, password.value, confirmPassword.value) }) {
            Text(
                "Registrate",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun SignUpScreenToolbar(onNavigationIconClick: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = { NavigationIcon { onNavigationIconClick() } }
    )
}
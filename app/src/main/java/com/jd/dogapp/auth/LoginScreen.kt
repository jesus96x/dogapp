package com.jd.dogapp.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: () -> Unit,
    viewModel: AuthViewModel
    //status: ApiResponseStatus<Any>? = null,
)
{
    Scaffold(
        topBar = { TopBar()  },
    ) {
        Content(onLoginButtonClick, onRegisterButtonClick)
    }
}

@Composable
private fun Content(onLoginButtonClick: (String, String) -> Unit, onRegisterButtonClick: () -> Unit)
{

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 16.dp, end = 16.dp, start = 16.dp,),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            email = email.value,
            onTextChanged = { email.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,),
            errorSemantic = "email-field-error",
            fieldSemantic = "email-field"
        )
        AuthField(
            email = password.value,
            onTextChanged = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,),
            errorSemantic = "password-field-error",
            fieldSemantic = "password-field"
        )
        Button(
            modifier = Modifier
                .fillMaxWidth().semantics { testTag = "login-button" }
                .padding(top = 16.dp),
            onClick = { onLoginButtonClick(email.value, password.value) }
        ) {
            Text("Login", textAlign = TextAlign.Center, fontWeight = FontWeight.Medium)
        }
        Text(text = "No tienes una tacuen?", textAlign = TextAlign.Center
            , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Text(
            text = "Registrate :3",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clickable(enabled = true, onClick = { onRegisterButtonClick() })
                .semantics { testTag = "register-button" }

        )
    }
}

@Composable
fun TopBar()
{
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,

    )
}
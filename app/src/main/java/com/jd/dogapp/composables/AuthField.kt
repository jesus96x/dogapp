package com.jd.dogapp.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(email: String, onTextChanged: (String) -> Unit, modifier: Modifier = Modifier
              , label: String
              , visualTransformation: VisualTransformation = VisualTransformation.None
              , keyboardOptions: KeyboardOptions
              , errorMessage: String? = null
              , errorSemantic: String = ""
              , fieldSemantic: String = ""
)
{
    Column(
        modifier = modifier,
    ) {
        if(errorMessage != null)
            Text(
                text = errorMessage,
                modifier = Modifier.fillMaxWidth().semantics { testTag = errorSemantic },
            )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .semantics { testTag = fieldSemantic },
            value = email,
            onValueChange = { onTextChanged(it) },
            label = { Text(label)  },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            isError = errorMessage != null
        )
    }
}
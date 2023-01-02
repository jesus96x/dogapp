package com.jd.dogapp.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.jd.dogapp.api.ApiResponseStatus

@Composable
fun ErrorDialog(status: ApiResponseStatus.Error<Any>, onDialogDismiss: () -> Unit)
{
    AlertDialog(
        modifier = Modifier.semantics { testTag = "error_dialog" },
        onDismissRequest = {  },
        title = { Text(text = "Oops error :3") },
        text = { Text(text = status.msg) },
        confirmButton = { Button(onClick = { onDialogDismiss() })
        {
            Text(text = "Intetalo de nuevo")
        }
        }
    )
}
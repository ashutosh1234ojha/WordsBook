package com.mywordsbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun UpdateAlertDialog(
    onSelection: () -> Unit,
    dialogText: String,
) {

    Dialog(onDismissRequest = { onSelection() }) {

        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = dialogText,
                    style = TextStyle(
                        color = Black
                    )

                )
                TextButton(
                    onClick = {
                        onSelection()
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Black
                        ), text = "Update"
                    )
                }

            }
        }


    }
}
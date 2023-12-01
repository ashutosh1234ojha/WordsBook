package com.example.mywordsbook

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
fun CustomAlertDialog(
    onSelection: (Int) -> Unit,
    onConfirmation: () -> Unit,
    dialogText: String,
    icon: ImageVector,
) {
//    AlertDialog(
//        icon = {
//            Icon(icon, contentDescription = "Icon")
//        },
//        text = {
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center,
//                text = dialogText
//            )
//        },
//        onDismissRequest = {
//            onDismissRequest()
//
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    onConfirmation()
//                }
//            ) {
//                Text("Confirm")
//            }
//        },
//        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text("Dismiss")
//            }
//        }
//    )

    Dialog(onDismissRequest = { onSelection(0) }) {

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
                    fontSize = 20.sp,
                    style = TextStyle(
                        color = Black
                    )

                )
                TextButton(
                    onClick = {
                        onSelection(1)
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Black
                        ), text = "Random"
                    )
                }
                TextButton(
                    onClick = {
                        onSelection(2)
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Black
                        ), text = "Old to New"
                    )
                }
                TextButton(
                    onClick = {
                        onSelection(3)
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Black
                        ), text = "New to Old"
                    )
                }
            }
        }


    }
}
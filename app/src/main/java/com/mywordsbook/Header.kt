package com.mywordsbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(
    headerText: String,
    isActionVisible: Boolean = false,
    firstAction: () -> Unit={},
    secondAction: () -> Unit={}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.primary))
            .height(50.dp),


        ) {

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = headerText,
            color = Color.White, fontSize = 20.sp
        )

        if(isActionVisible){
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {

                IconButton(onClick = {
                    //  showDialog = !showDialog
                    firstAction()
                }) {
                    Image(
                        painterResource(R.drawable.baseline_filter_alt_24),
                        contentDescription = "Shuffle",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                    )
                }
                IconButton(onClick = {
//                homeViewModel._setSelectedWord(null)
//                navController.navigate("AddWordScreen")
                    secondAction()
                }) {
                    Icon(Icons.Filled.Add, "Floating action button.", tint = Color.White)

                }
            }
        }


    }
}
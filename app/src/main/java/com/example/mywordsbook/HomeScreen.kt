package com.example.mywordsbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val list by homeViewModel.getSavedWords().collectAsState(arrayListOf())
    Column(modifier = Modifier.padding(5.dp)) {

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(list) { item ->
                Text(text = item.wording, fontSize = 18.sp, color = Color.Black)

                Text(text = item.meaning, fontSize = 16.sp, color = Color.Gray)
                Spacer(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color.Black)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate("AddWordScreen")

                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }

    }
}
package com.example.mywordsbook

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SettingScreen(navController: NavHostController, commonViewModel: CommonViewModel) {
    Column {
        Text(text = "SettingScreen")
    }
}
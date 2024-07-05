package com.mywordsbook

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SettingScreen(navController: NavHostController, commonViewModel: CommonViewModel) {
    val isSwitchOn by commonViewModel.isSwitchOn.collectAsState()
    val isDarkTheme by commonViewModel.isDarkTheme.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Header(
            headerText = "Setting Screen"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you want to switch to list view", style = TextStyle(
                    color =  if (isDarkTheme) Color.White else Color.Black,
                    fontSize = 15.sp,
                )
            )
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { newCheckedValue ->
                    commonViewModel.updateListView(newCheckedValue)
                }
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you want to Dark theme", style = TextStyle(
                    color = if (isDarkTheme) Color.White else Color.Black,
                    fontSize = 15.sp,
                )
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { newCheckedValue ->
                    commonViewModel.updateTheme(newCheckedValue)
                }
            )

        }

    }
}

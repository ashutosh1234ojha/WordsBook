package com.mywordsbook

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun SettingScreen(
    navController: NavHostController,
    commonViewModel: CommonViewModel,
    userData: UserData?, signIn: () -> Unit, signOut: () -> Unit
) {
    val isSwitchOn by commonViewModel.isSwitchOn.collectAsState()
    val isDarkTheme by commonViewModel.isDarkTheme.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Header(
            headerText = "Setting Screen"
        )
        Spacer(modifier = Modifier.height(20.dp))


        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape), contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = R.drawable.baseline_person_24,
                contentDescription = "Profile image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(1.dp, color = Color.Black, CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        if (userData?.username != null) {
            Text(
                text = userData.username,
                style = TextStyle(
                    color = if (isDarkTheme) Color.White else Color.Black,
                    fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                ),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier.clickable {
                    signOut()
                },
                text = "Sign Out", style = TextStyle(
                    color = if (isDarkTheme) Color.White else Color.Black,
                    fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                )

            )
        } else {
            Text(
                modifier = Modifier.clickable {
                    signIn()
                },
                text = "Sign In", style = TextStyle(
                    color = if (isDarkTheme) Color.White else Color.Black,
                    fontSize = 15.sp, fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you want to switch to list view", style = TextStyle(
                    color = if (isDarkTheme) Color.White else Color.Black,
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
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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

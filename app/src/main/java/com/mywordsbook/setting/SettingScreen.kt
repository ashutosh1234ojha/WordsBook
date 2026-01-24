package com.mywordsbook.setting

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mywordsbook.core.viewmodel.CommonViewModel
import com.mywordsbook.core.ui.Header
import com.mywordsbook.R
import com.mywordsbook.login.UserData


@Composable
fun SettingScreen(
    navController: NavHostController,
    commonViewModel: CommonViewModel,
     settingViewModel: SettingViewModel = hiltViewModel()


) {
    val isSwitchOn by commonViewModel.isSwitchOn.collectAsState()
    val isDarkTheme by commonViewModel.isDarkTheme.collectAsState()

//    val settingViewModel = viewModel<SettingViewModel>()

    val coroutineScope = rememberCoroutineScope()
    val context= LocalContext.current

    val userData: UserData? = settingViewModel.userDataState.collectAsState().value


    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // coroutineScope.launch {
                    settingViewModel.googleLogin(result)
                    // }


                    settingViewModel.scheduleSync(context)

                }
            })


    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Header(
            headerText = stringResource(R.string.setting_screen)
        )
        Spacer(modifier = Modifier.height(20.dp))


        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = stringResource(R.string.profile_image),
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape), contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = R.drawable.baseline_person_24,
                contentDescription = stringResource(R.string.profile_image),
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
                style = MaterialTheme.typography.labelLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier.clickable {
                    //signOut()
                    settingViewModel.googleLogout()
                    navController.popBackStack()
                },
                text = stringResource(R.string.sign_out),
                style = MaterialTheme.typography.labelLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)


            )
        } else {
            Text(
                modifier = Modifier.clickable {
                    //   signIn()
//                    coroutineScope.launch {
////                        googleAuthUiClient.signIn()?.let {
////                            launcher.launch(
////                                IntentSenderRequest.Builder(it).build()
////                            )
////                        }
//
////                        settingViewModel.googleLogin(){
//
//                        }


//                    }
                    settingViewModel.launchLogin { intentSender ->
                        launcher.launch(
                            IntentSenderRequest.Builder(intentSender).build()
                        )


                    }
                },
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.labelLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)

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
                text = stringResource(R.string.do_you_want_to_switch_to_list_view),
                style = MaterialTheme.typography.labelLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)

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
                text = stringResource(R.string.do_you_want_to_dark_theme),
                style = MaterialTheme.typography.labelLarge.copy(color = if (isDarkTheme) Color.White else Color.Black)

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

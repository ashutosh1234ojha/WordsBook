package com.mywordsbook.setting

import android.content.Context
import android.content.IntentSender
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mywordsbook.core.network.GoogleAuthUiClient
import com.mywordsbook.core.network.NetworkCallWorkManager
import com.mywordsbook.login.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(val googleAuthUiClient: GoogleAuthUiClient) :
    ViewModel() {

        val _userDataState= MutableStateFlow<UserData?>(null)
    val userDataState= _userDataState



    fun launchLogin(launch:(IntentSender)->Unit){
       viewModelScope.launch {
           googleAuthUiClient.signIn()?.let {
               launch(it)
           }
       }

    }

    fun googleLogin(result: ActivityResult) {
        viewModelScope.launch {
            val signInResult = googleAuthUiClient.signInWithIntent(
                intent = result.data ?: return@launch
            )

            _userDataState.value=signInResult.data

        }


////                                    scheduleSync()
//
//
//                        commonViewModel.onSignInResult(signInResult)

    }

    fun googleLogout(){
        viewModelScope.launch {
            googleAuthUiClient.signOut()


        }
    }

    fun scheduleSync(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        val data = Data.Builder().putString("User_id", googleAuthUiClient.getSignedInUser()?.userId)
            .build()

        val syncWorkRequest =
            PeriodicWorkRequestBuilder<NetworkCallWorkManager>(1, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

        WorkManager.getInstance(context).enqueue(
            syncWorkRequest
        )
    }


}
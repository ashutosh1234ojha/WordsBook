package com.mywordsbook.setting

import android.content.Context
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mywordsbook.core.network.GoogleAuthUiClient
import com.mywordsbook.core.network.NetworkCallWorkManager
import com.mywordsbook.db.WordBackend
import com.mywordsbook.login.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(val googleAuthUiClient: GoogleAuthUiClient) :
    ViewModel() {

    val _userDataState = MutableStateFlow<UserData?>(null)
    val userDataState = _userDataState


    fun launchLogin(launch: (IntentSender) -> Unit) {
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

            _userDataState.value = signInResult.data

        }


////                                    scheduleSync()
//
//
//                        commonViewModel.onSignInResult(signInResult)

    }

    fun googleLogout() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()


        }
    }

    fun fetchAllDataFromServer() {
        val firebaseUserId = googleAuthUiClient.getSignedInUser()?.userId ?: ""

        val collectionRef =
            Firebase.firestore
                .collection("Users")
                .document(firebaseUserId)
                .collection("Words")

        collectionRef.get().addOnSuccessListener {result->
            val users = result.toObjects(WordBackend::class.java)
            users.forEach {
                Log.d("Firestore", it.toString())
            }
        }


    }

    fun scheduleSync(context: Context) {

        fetchAllDataFromServer()
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
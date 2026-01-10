package com.mywordsbook

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mywordsbook.core.data.BottomNavigationItem
import com.mywordsbook.ui.theme.MyWordsBookTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.mywordsbook.core.network.GoogleAuthUiClient
import com.mywordsbook.core.network.NetworkCallWorkManager
import com.mywordsbook.core.viewmodel.CommonViewModel
import com.mywordsbook.home.AddWordScreen
import com.mywordsbook.home.HomeScreen
import com.mywordsbook.quiz.QuizScreen
import com.mywordsbook.setting.SettingScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var commonViewModel: CommonViewModel
    private lateinit var auth: FirebaseAuth
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(applicationContext, Identity.getSignInClient(applicationContext))
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)


        setContent {
            commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
            auth = Firebase.auth
            val isDarkTheme by commonViewModel.isDarkTheme.collectAsState()


            val navController = rememberNavController()

            val list =
                listOf(
                    BottomNavigationItem(
                        "Home",
                        Icons.Filled.Home,
                        Icons.Outlined.Home,
                        "HomeScreen"
                    ),
                    BottomNavigationItem(
                        "Quiz",
                        ImageVector.vectorResource(R.drawable.test_icon_outline),
                        ImageVector.vectorResource(R.drawable.test_icon),
                        "QuizScreen"
                    ),
                    BottomNavigationItem(
                        "Setting",
                        Icons.Filled.Settings,
                        Icons.Outlined.Settings,
                        "SettingScreen"
                    )
                )
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }

            MyWordsBookTheme(isDarkTheme) {
                Scaffold(bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    if (currentRoute != "LoginScreen") {
                        NavigationBar {

                            list.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(item.route)

                                    },
                                    label = { item.title },
                                    icon = {

                                        Icon(
                                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    })

                            }
                        }

                    }
                })
                { innerPadding ->
                    Host(commonViewModel = commonViewModel, navController, innerPadding)

                }


            }
        }
    }

    @Composable
    private fun Host(
        commonViewModel: CommonViewModel,
        navController: NavHostController,
        innerPadding: PaddingValues
    ) {
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "HomeScreen"
        ) {
            composable("HomeScreen") {
                HomeScreen(navController, commonViewModel)
            }

            composable(
                "QuizScreen"
            ) {
                QuizScreen(navController, commonViewModel)
            }

            composable(
                "SettingScreen"
            ) {
                val state by commonViewModel.state.collectAsStateWithLifecycle()
                val launcher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == Activity.RESULT_OK) {

//                            lifecycleScope.launch {  }
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    scheduleSync()


                                    commonViewModel.onSignInResult(signInResult)
                                }

                            }
                        })
                SettingScreen(
                    navController,
                    commonViewModel,
                    googleAuthUiClient.getSignedInUser(),
                    {
                        //Todo
                        lifecycleScope.launch {
                            googleAuthUiClient.signIn()?.let {
                                launcher.launch(
                                    IntentSenderRequest.Builder(it).build()
                                )
                            }


                        }
                    }) {
                    lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        navController.popBackStack()

                    }
                }
            }
            composable(
                "AddWordScreen"
            ) {
                AddWordScreen(navController, commonViewModel)
            }
            composable(
                "LoginScreen"
            ) {


//                LoginScreen(state = state, onSignInClick = {
//                    lifecycleScope.launch {
//                        val signInIntentSender = googleAuthUiClient.signIn()
//                        launcher.launch(IntentSenderRequest.Builder(signInIntentSender!!).build())
//                    }
//                })

            }

//            LoginScreen(navController, commonViewModel)
        }


    }

    fun scheduleSync() {

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

        WorkManager.getInstance(this).enqueue(
            syncWorkRequest
        )
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}

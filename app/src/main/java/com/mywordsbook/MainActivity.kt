package com.mywordsbook

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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
import com.mywordsbook.data.BottomNavigationItem
import com.mywordsbook.ui.theme.MyWordsBookTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var commonViewModel: CommonViewModel
    private lateinit var auth: FirebaseAuth
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(applicationContext, Identity.getSignInClient(applicationContext))
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
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
                Surface(modifier = Modifier.fillMaxSize()) {

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
                        val a = innerPadding
                        Host(commonViewModel = commonViewModel, navController)

                    }


                }

            }

        }
    }

    @Composable
    private fun Host(commonViewModel: CommonViewModel, navController: NavHostController) {
        NavHost(navController = navController, startDestination = "HomeScreen") {
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

                                    commonViewModel.onSignInResult(signInResult)
                                }

                            }
                        })
                SettingScreen(
                    navController,
                    commonViewModel,
                    googleAuthUiClient.getSignedInUser(),
                    {
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(signInIntentSender!!).build()
                            )
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


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}

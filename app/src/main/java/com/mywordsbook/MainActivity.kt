package com.mywordsbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mywordsbook.data.BottomNavigationItem
import com.mywordsbook.ui.theme.MyWordsBookTheme
import com.mywordsbook.AddWordScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var commonViewModel: CommonViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BuildConfig.VERSION_NAME
        setContent {
            commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
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
                    })
                    { innerPadding ->
                        val a = innerPadding
                        Host(commonViewModel = commonViewModel, navController)

                    }


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
            SettingScreen(navController, commonViewModel)
        }
        composable(
            "AddWordScreen"
        ) {
            AddWordScreen(navController, commonViewModel)
        }


    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}

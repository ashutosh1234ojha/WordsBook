package com.example.mywordsbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mywordsbook.ui.theme.MyWordsBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var commonViewModel: CommonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column {
                Host(commonViewModel)
            }

        }
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
    }


}

@Composable
private fun Host(commonViewModel: CommonViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController, commonViewModel)
        }

        composable(
            "AddWordScreen"
        ) {
            AddWordScreen(navController, commonViewModel)
        }

    }
}

@Composable
fun ActionFloatingBtn(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWordsBookTheme {
        Greeting("Android")
    }
}
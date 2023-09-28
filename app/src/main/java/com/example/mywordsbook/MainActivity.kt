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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mywordsbook.db.WordDatabase
import com.example.mywordsbook.ui.theme.MyWordsBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var mViewModel: AddWordViewModel
    lateinit var homeVM: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column {
                Host(mViewModel,homeVM)
            }

        }
        mViewModel = ViewModelProvider(this).get(AddWordViewModel::class.java)
        homeVM = ViewModelProvider(this).get(HomeViewModel::class.java)
    }


}

@Composable
private fun Host(mViewModel: AddWordViewModel,homeVM:HomeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController,homeVM)
        }

        composable("AddWordScreen") {
            AddWordScreen(navController,mViewModel)
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
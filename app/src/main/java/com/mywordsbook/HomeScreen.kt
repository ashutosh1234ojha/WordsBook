package com.mywordsbook

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.mywordsbook.db.Word


var lastDate = ""

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: CommonViewModel,
) {
    val context = LocalContext.current


    var mutableList by remember {
        mutableStateOf<List<Word>>(emptyList()) // Initial state
    }
    var showDialog by remember { mutableStateOf(false) }
    //  var showVersionDialog by remember { mutableStateOf(false) }
    var lastDate by remember { mutableStateOf("") }
    val isSwitchOn by homeViewModel.isSwitchOn.collectAsState()
    val isDarkTheme by homeViewModel.isDarkTheme.collectAsState()


    mutableList = homeViewModel.list.collectAsState(initial = emptyList()).value
    val showVersionDialog by homeViewModel.currentVersion.collectAsState()

    if (showDialog) {
        CustomAlertDialog(
            onSelection = {
                when (it) {
                    1 -> homeViewModel.getSavedWords(true)
                    2 -> {
                        lastDate = ""
                        homeViewModel.getSavedWordsLatestFirst(true)
                    }

                    3 -> {
                        lastDate = ""
                        homeViewModel.getSavedWordsLatestFirst(false)
                    }

                    else -> {

                    }
                }
                showDialog = !showDialog
            },
            dialogText = "sort by...",
        )
    }

    if (showVersionDialog > BuildConfig.VERSION_CODE) {
        UpdateAlertDialog(onSelection = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.mywordsbook")
            )
            context.startActivity(intent)
        }, dialogText = "Please update your current app version")
    }



    Column(modifier = Modifier) {

        Header(
            headerText = "Word List", isActionVisible = true,
            firstAction = { showDialog = !showDialog },
            secondAction = {
                homeViewModel._setSelectedWord(null)
                navController.navigate("AddWordScreen")
            })

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 80.dp, start = 10.dp, end = 10.dp)
        ) {
            items(mutableList) { item ->
                if (isSwitchOn) {
                    WordListUI(item = item, isDarkTheme = isDarkTheme) { _ ->
                        homeViewModel._setSelectedWord(item)
                        navController.navigate("AddWordScreen")
                    }
                } else {
                    WordCardUI(item = item) { _ ->
                        homeViewModel._setSelectedWord(item)
                        navController.navigate("AddWordScreen")
                    }
                }

            }
        }


    }

}


@Composable
fun LoadingView(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Loading.. Please wait..",
                    Modifier
                        .padding(8.dp), textAlign = TextAlign.Center
                )

                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
    }
}


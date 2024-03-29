package com.example.mywordsbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.mywordsbook.db.Word

var lastDate = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: CommonViewModel
) {
    var mutableList by remember {
        mutableStateOf<List<Word>>(emptyList()) // Initial state
    }
    var showDialog by remember { mutableStateOf(false) }
    var lastDate by remember { mutableStateOf("") }

    mutableList = homeViewModel.list.collectAsState(initial = emptyList()).value

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
            dialogText = "Choose the filter option",
        )
    }



    Column(modifier = Modifier.padding(5.dp)) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Word List") },
            actions = {
                IconButton(onClick = {
                    showDialog = !showDialog
                }) {
                    Image(
                        painterResource(R.drawable.baseline_filter_alt_24),
                        contentDescription = "Shuffle",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
                IconButton(onClick = {
                    homeViewModel._setSelectedWord(null)
                    navController.navigate("AddWordScreen")
                }) {
                    Icon(Icons.Filled.Add, "Floating action button.")

                }
            }
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(mutableList) { item ->
//                ItemList(item) { selectedId ->
//                    homeViewModel._setSelectedWord(item)
//                    navController.navigate("AddWordScreen")
//                }
                WordCardUI(item = item) { selectedId ->
                    homeViewModel._setSelectedWord(item)
                    navController.navigate("AddWordScreen")
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


@Composable
fun ItemList(item: Word, onClick: (id: Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(2.dp),
    ) {
        val currentDate = item.createdDateTime.substringBefore(" ")
        if (!lastDate.equals(currentDate)) {
            lastDate = currentDate
            Text(text = lastDate, fontSize = 16.sp, color = Color.DarkGray)

        }

        Column(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color(0xFFADDFAD))
                .padding(10.dp),

            ) {

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.wording,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    Icons.Default.Create,
                    contentDescription = "",
                    Modifier
                        .clickable { onClick(item.id) }
                        .padding(10.dp)
                )
            }

            Text(text = item.meaning, fontSize = 16.sp, color = Color.DarkGray)
        }
    }


}



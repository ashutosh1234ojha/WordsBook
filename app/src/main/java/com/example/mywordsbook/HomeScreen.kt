package com.example.mywordsbook

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mywordsbook.db.Word
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: CommonViewModel
) {
    val list by homeViewModel.getSavedWords(true).collectAsState(arrayListOf())
    Column(modifier = Modifier.padding(5.dp)) {

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(list) { item ->
                ItemList(item) { selectedId ->
                    homeViewModel._setSelectedWord(item)
                    navController.navigate("AddWordScreen")


                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    homeViewModel._setSelectedWord(null)
                    navController.navigate("AddWordScreen")

                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
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
            .background(Color(0xFFADDFAD))
            .padding(10.dp),

        ) {
        Text(text = item.wording, fontSize = 18.sp, color = Color.Black)

        Icon(
            Icons.Default.Create,
            contentDescription = "",
            Modifier
                .align(Alignment.End)
                .clickable {
                    onClick(item.id)

                },


            )
        Text(text = item.meaning, fontSize = 16.sp, color = Color.DarkGray)


    }


}

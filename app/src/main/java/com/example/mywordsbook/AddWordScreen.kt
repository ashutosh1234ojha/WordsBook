package com.example.mywordsbook

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
    navController: NavHostController,
    addWordViewModel: CommonViewModel,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        var word by rememberSaveable {
            mutableStateOf(
                addWordViewModel.selectedWord?.wording ?: ""
            )
        }
        var meaning by rememberSaveable {
            mutableStateOf(
                addWordViewModel.selectedWord?.meaning ?: ""
            )
        }
        var isImportant by rememberSaveable {
            mutableStateOf(
                addWordViewModel.selectedWord?.isImportant ?: false
            )
        }
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = word, onValueChange = {
            word = it
        }, label = { Text(text = "Enter word") }
        )

        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = meaning, onValueChange = {
            meaning = it

        }, label = { Text(text = "Enter Meaning") }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                addWordViewModel.saveWordMeaning(word, meaning, isImportant)
                navController.popBackStack()

            }) {
                Text(text = "Save")
            }
            IconButton(onClick = {
                isImportant = !isImportant
            }) {
                Image(
                    painterResource(if (isImportant) R.drawable.baseline_star_24 else R.drawable.baseline_star_outline_24),
                    contentDescription = "Important",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
            Button(onClick = {
                addWordViewModel.deleteWord()
                navController.popBackStack()

            }) {
                Text(text = "Delete")
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun AddWordScreenPreview() {

}
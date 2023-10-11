package com.example.mywordsbook

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mywordsbook.ui.theme.MyWordsBookTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
                addWordViewModel.saveWordMeaning(word, meaning)
                navController.popBackStack()

            }) {
                Text(text = "Save")
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
    MyWordsBookTheme {
//        AddWordScreen()
    }
}
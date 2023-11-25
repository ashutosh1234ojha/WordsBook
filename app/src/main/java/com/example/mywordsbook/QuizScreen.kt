package com.example.mywordsbook

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mywordsbook.db.Word
import kotlinx.coroutines.delay
import java.util.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizScreen(navController: NavHostController, commonViewModel: CommonViewModel) {

    var firstFour by remember {
        mutableStateOf<List<Word>>(emptyList()) // Initial state
    }


    firstFour = commonViewModel.quizWordOptions.collectAsState(initial = emptyList()).value

    var word: Word? = null
    var listWordsNew: List<Word>? = null
    if (firstFour.isNotEmpty()) {
        word = firstFour.get(0)
        listWordsNew = firstFour.shuffled()
    }

    val selectedOption = remember { mutableStateOf("") }
    val correctOption = remember { mutableStateOf(word?.meaning ?: "") }
    val color = remember { mutableStateOf(Color.Cyan) }


    // Display 10 items
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            commonViewModel.getQuizOptions()
            selectedOption.value=""

        }
    }
    HorizontalPager(state = pagerState) { page ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(25.dp, 25.dp, 25.dp, 25.dp))
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = word?.wording ?: "",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color.value),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption.value == (listWordsNew?.get(0)?.meaning ?: ""),
                        onClick = {
                            selectedOption.value = listWordsNew?.get(0)?.meaning ?: ""
//                            color.value =
//                                getBgColor(correctOption.value, listWordsNew?.get(0)?.meaning ?: "")
                        }

                    )
                    Text(listWordsNew?.get(0)?.meaning ?: "")

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color.value),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption.value == (listWordsNew?.get(1)?.meaning ?: ""),
                        onClick = {
                            selectedOption.value = listWordsNew?.get(1)?.meaning ?: ""
                          //  color.value =
                              //  getBgColor(correctOption.value, listWordsNew?.get(1)?.meaning ?: "")

                        }

                    )
                    Text(listWordsNew?.get(1)?.meaning ?: "")

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color.value),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption.value == (listWordsNew?.get(2)?.meaning ?: ""),
                        onClick = {
                            selectedOption.value = listWordsNew?.get(2)?.meaning ?: ""
//                            color.value =
                                //getBgColor(correctOption.value, listWordsNew?.get(2)?.meaning ?: "")

                        }

                    )
                    Text(listWordsNew?.get(2)?.meaning ?: "")

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color.value),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption.value == (listWordsNew?.get(3)?.meaning ?: ""),
                        onClick = {
                            selectedOption.value = listWordsNew?.get(3)?.meaning ?: ""
//                            if(selectedOption.value==correctOption.value){
//
//                            }
//                            color.value =
                              //  getBgColor(correctOption.value, listWordsNew?.get(3)?.meaning ?: "")

                        }

                    )
                    Text(listWordsNew?.get(3)?.meaning ?: "")

                }
                Text(
                    text = "Result ${selectedOption.value==word?.meaning}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
            }
        }



    }

}

fun getBgColor(correctAnswer: String, selectedOption: String): Color {
    return if (correctAnswer.equals(selectedOption)) Color.Green else Color.Red
}
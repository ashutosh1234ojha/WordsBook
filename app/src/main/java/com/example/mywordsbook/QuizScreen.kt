package com.example.mywordsbook

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mywordsbook.db.Word
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import java.util.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizScreen(navController: NavHostController, commonViewModel: CommonViewModel) {
    val myState by commonViewModel.score.collectAsState()

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
            selectedOption.value = ""

        }
    }
    HorizontalPager(state = pagerState) { page ->

        Column {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Score ${myState}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        fontSize = 20.sp
                    )
                )
            }
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                    .background(Color(0xFFF5F5DC))
                    .padding(vertical = 50.dp)
            ) {
                Text(
                    modifier = Modifier.background(Color.Transparent),
                    text = buildAnnotatedString {
                        append("What the meaning of ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(word?.wording ?: "")
                        }
                        append("?")
                    },
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        fontSize = 20.sp, textAlign = TextAlign.Center
                    )
                )


            }

            QuizOption(0, option = listWordsNew?.get(0)?.meaning ?: "") {
                selectedOption.value = listWordsNew?.get(0)?.meaning ?: ""
                commonViewModel.updateScore(selectedOption.value == word?.meaning)


            }
            QuizOption(1, option = listWordsNew?.get(1)?.meaning ?: "") {
                selectedOption.value = listWordsNew?.get(1)?.meaning ?: ""
                commonViewModel.updateScore(selectedOption.value == word?.meaning)


            }
            QuizOption(2, option = listWordsNew?.get(2)?.meaning ?: "") {
                selectedOption.value = listWordsNew?.get(2)?.meaning ?: ""
                commonViewModel.updateScore(selectedOption.value == word?.meaning)


            }
            QuizOption(3, option = listWordsNew?.get(3)?.meaning ?: "") {
                selectedOption.value = listWordsNew?.get(3)?.meaning ?: ""
                commonViewModel.updateScore(selectedOption.value == word?.meaning)


            }

        }


    }

}


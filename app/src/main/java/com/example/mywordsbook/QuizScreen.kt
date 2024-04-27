package com.example.mywordsbook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mywordsbook.db.Word
import kotlinx.coroutines.launch

var currentPage = 0

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
            currentPage = page
            commonViewModel.getQuizOptions()
            selectedOption.value = ""

        }
    }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (firstFour.size >= 4) {
            HorizontalPager(state = pagerState) { page ->

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Button(onClick = {
                            coroutineScope.launch {

                                pagerState.animateScrollToPage(currentPage + 1)


                            }
                        }) {
                            Text(
                                text = "Next Quiz",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Green,
                                    fontSize = 20.sp
                                )
                            )
                        }
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
                        handleQuizClick(0, selectedOption, commonViewModel, listWordsNew, word) {
                            coroutineScope.launch {

                                pagerState.animateScrollToPage(currentPage + 1)


                            }
                        }


                    }
                    QuizOption(1, option = listWordsNew?.get(1)?.meaning ?: "") {
                        handleQuizClick(
                            1,
                            selectedOption,
                            commonViewModel,
                            listWordsNew,
                            word
                        ) {
                            coroutineScope.launch {

                                pagerState.animateScrollToPage(currentPage + 1)


                            }
                        }


                    }
                    QuizOption(2, option = listWordsNew?.get(2)?.meaning ?: "") {
                        handleQuizClick(
                            2,
                            selectedOption,
                            commonViewModel,
                            listWordsNew,
                            word,
                        ) {
                            coroutineScope.launch {

                                pagerState.animateScrollToPage(currentPage + 1)


                            }
                        }


                    }
                    QuizOption(3, option = listWordsNew?.get(3)?.meaning ?: "") {
                        handleQuizClick(
                            3,
                            selectedOption,
                            commonViewModel,
                            listWordsNew,
                            word,
                        ) {
                            coroutineScope.launch {

                                pagerState.animateScrollToPage(currentPage + 1)


                            }
                        }


                    }

                }


            }

        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(R.drawable.add_more_words),
                    contentDescription = "Shuffle",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )

                Text(
                    text = "Please add at least four words for\n the quiz", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 20.sp, textAlign = TextAlign.Center
                    )
                )
            }

        }

    }

}

fun handleQuizClick(
    index: Int,
    selectedOption: MutableState<String>,
    commonViewModel: CommonViewModel,
    listWordsNew: List<Word>?,
    word: Word?,
    onClick: () -> Unit
) {
    selectedOption.value = listWordsNew?.get(index)?.meaning ?: ""
    commonViewModel.updateScore(selectedOption.value == word?.meaning)
    onClick()


}


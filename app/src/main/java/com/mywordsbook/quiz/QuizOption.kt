package com.mywordsbook.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mywordsbook.R
import com.mywordsbook.ui.theme.Correct_Answer


@Composable
fun QuizOption(
    number: Int,
    option: String,
    meaning: String = "",
    isDarkTheme: Boolean = false,
    onClick: () -> Unit
) {
    var isTrue by remember {
        mutableStateOf(false) // Initial state
    }

    Column {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${number + 1}.",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        if (option == meaning) {
                            isTrue = true
                        }
                        onClick()

                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isTrue) Correct_Answer else colorResource(id = R.color.primary))
                        .clip(shape = RoundedCornerShape(50.dp))
                        .padding(10.dp),
                    text = option,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color =  Color.White
                    )
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
    }


}
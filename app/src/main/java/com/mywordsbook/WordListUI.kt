package com.mywordsbook

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mywordsbook.db.Word


@Composable
fun WordListUI(
    modifier: Modifier = Modifier, // Modifier as Parameter
    isDarkTheme: Boolean = false,
    item: Word, onClick: (id: Int) -> Unit
) {

    Box {
        Column(
            modifier = Modifier
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
                    color = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Icon(
                    Icons.Default.Create,
                    contentDescription = "",
                    Modifier
                        .clickable { onClick(item.id) }
                        .padding(10.dp),
                    tint = if (isDarkTheme) Color.White else Color.Black,

                    )
            }

            Text(
                text = item.meaning,
                fontSize = 16.sp,
                color = if (isDarkTheme) Color.White else Color.Black
            )

            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(if (isDarkTheme) Color.White else Color.Black)
            )
        }

    }

//    }

}



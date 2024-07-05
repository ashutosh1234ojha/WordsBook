package com.mywordsbook.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Dark_Primary,
    onPrimary = Dark_On_Primary,
    secondary = Dark_Secondary,
    background = Dark_Background,
    onBackground = Dark_On_Background,
    onSecondary = Dark_On_Secondary,
    onSurface = Dark_On_Surface
)

private val LightColorScheme = lightColorScheme(
    primary = Light_Primary,
    onPrimary = Light_On_Primary,
    secondary = Light_Secondary,
    background = Light_Background,
    onBackground = Light_On_Background,
    onSecondary = Light_On_Secondary,
    onSurface = Light_On_Surface

)

@Composable
fun MyWordsBookTheme(
    useDarkTheme: Boolean = true,
    content: @Composable() () -> Unit
) {

    val colors = if (!useDarkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    val myColorr=Color.Black

    MaterialTheme(
        colorScheme = colors,
        content = content
    )

}
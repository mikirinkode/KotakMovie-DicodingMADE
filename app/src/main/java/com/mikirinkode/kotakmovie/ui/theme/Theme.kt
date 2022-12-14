package com.mikirinkode.kotakmovie.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Primary500,
    primaryVariant = Primary700,
    secondary = Secondary700,
    secondaryVariant = Secondary900,

    background = Dark500,
    surface = Dark500,

    onPrimary = Dark400,
    onSecondary = Color.White, // Used as Text Color
    onBackground = Light500,
    onSurface = Light500,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KotakMovieTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
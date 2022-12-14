package com.mikirinkode.kotakmovie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun KotakMovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Test Compose", modifier = modifier.align(Alignment.Center), fontSize = 22.sp)
    }
}

@Preview
@Composable
fun KotakMovieAppPreview() {
    KotakMovieTheme {
        KotakMovieApp()
    }
}
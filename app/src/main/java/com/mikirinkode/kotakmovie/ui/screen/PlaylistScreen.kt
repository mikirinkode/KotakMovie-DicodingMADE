package com.mikirinkode.kotakmovie.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun PlaylistScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "PLAYLIST", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun PlaylistScreenPreview() {
    KotakMovieTheme {
        PlaylistScreen()
    }
}

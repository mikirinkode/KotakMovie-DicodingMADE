package com.mikirinkode.kotakmovie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun StateMessageComponent(
    drawableId: Int,
    drawableDesc: Int,
    imageWidth: Int,
    imageHeight: Int,
    titleStringId: Int,
    descriptionStringId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = stringResource(id = drawableDesc),
            modifier = Modifier.size(width = imageWidth.dp, height = imageHeight.dp)
        )
        Text(
            text = stringResource(id = titleStringId),
            fontSize = 24.sp,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
        )
        Text(
            text = stringResource(id = descriptionStringId),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
        )
    }
}

@Preview
@Composable
fun StateMessageComponentPreview() {
    KotakMovieTheme {
        StateMessageComponent(
            R.drawable.ic_empty_playlist_illustration,
            R.string.empty_playlist_illustration,
            200,
            250,
            R.string.empty_playlist_title,
            R.string.empty_playlist_description
        )
    }
}
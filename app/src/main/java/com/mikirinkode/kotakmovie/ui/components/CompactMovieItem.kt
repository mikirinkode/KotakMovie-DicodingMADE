package com.mikirinkode.kotakmovie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.R

@Composable
fun CompactMovieItem(
    imageUrl: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(
        0f to Color.Transparent,
        1000f to MaterialTheme.colors.background
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .wrapContentSize()
    ) {
        Box(modifier = Modifier){
            AsyncImage(
                model = imageUrl,
                contentDescription = "movie poster",
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .background(brush = gradient)
                    .clip(RoundedCornerShape(16.dp))
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = MaterialTheme.colors.background, shape = RoundedCornerShape(16f))
                    .padding(4.dp)
                    .align(Alignment.BottomCenter),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription  = "Rating Icon",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(15.dp)
                )
                Text(
                    text = rating.toString(),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompactMovieItemPreview() {
    KotakMovieTheme {

    }
}
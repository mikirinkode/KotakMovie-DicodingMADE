package com.mikirinkode.kotakmovie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun TrendingMovieItem(
    imageUrl: String,
    title: String,
    rating: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(
        0f to Color.Transparent,
        1000f to MaterialTheme.colors.background
    )

    Card(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
            .wrapContentSize()
            .clickable { onClick() }
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "$title Image",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .size(width = 320.dp, height = 180.dp)
                    .aspectRatio(16f / 9f)
            )
            Box(
                Modifier
                    .width(320.dp)
                    .height(180.dp)
                    .background(brush = gradient)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .width(320.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Row(
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Rating Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TrendingMovieItemPreview() {
    KotakMovieTheme {
    }
}
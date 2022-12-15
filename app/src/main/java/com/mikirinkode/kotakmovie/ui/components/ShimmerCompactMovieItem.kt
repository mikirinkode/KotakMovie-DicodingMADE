package com.mikirinkode.kotakmovie.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme


@Composable
fun ShimmerCompactMovieItem(
) {
    val shimmerColors = listOf(
        MaterialTheme.colors.secondaryVariant.copy(alpha = 0.7f),
        MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
        MaterialTheme.colors.secondaryVariant.copy(alpha = 0.7f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .wrapContentSize()
    ) {
        Box(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .background(brush = brush)
                    .clip(RoundedCornerShape(16.dp))
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(brush, shape = RoundedCornerShape(16f))
                    .padding(4.dp)
                    .width(40.dp)
                    .height(20.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShimmerCompactPreview() {
    KotakMovieTheme {
        ShimmerCompactMovieItem()
    }
}
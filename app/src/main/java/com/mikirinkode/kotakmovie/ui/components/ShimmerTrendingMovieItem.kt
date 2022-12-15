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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp


@Composable
fun ShimmerTrendingMovieItem(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        MaterialTheme.colors.secondaryVariant.copy(alpha = 0.7f),
        MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
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
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
            .size(width = 320.dp, height = 180.dp)
            .wrapContentSize()
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
        ) {
            Box(
                modifier = modifier
                    .size(width = 320.dp, height = 180.dp)
                    .aspectRatio(16f / 9f)
                    .background(brush)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
                    .width(320.dp)
                    .height(32.dp)
                    .background(brush, RoundedCornerShape(16.dp))
            )
        }
    }
}

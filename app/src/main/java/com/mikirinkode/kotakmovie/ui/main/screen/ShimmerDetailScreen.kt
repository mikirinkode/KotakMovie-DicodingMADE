package com.mikirinkode.kotakmovie.ui.main.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.utils.Constants
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.DetailMovieViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun ShimmerDetailScreen(
    navigateBack: () -> Unit,
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

    val state = rememberCollapsingToolbarScaffoldState()

    Box() {
        CollapsingToolbarScaffold(
            modifier = modifier.fillMaxSize(),
            state = state,
            toolbarModifier = Modifier.background(MaterialTheme.colors.secondaryVariant),
            enabled = true,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                Box(
                    modifier = Modifier
                        .parallax(0.5f)
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(brush)
                        .graphicsLayer {
                            // change alpha of image as the toolbar expands
                            alpha = state.toolbarState.progress
                        }
                )

                Box(
                    Modifier
                        .parallax(0.5f)
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush
                        )
                )
            }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = MaterialTheme.colors.secondaryVariant,
                        modifier = modifier
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            // title
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(32.dp)
                                    .background(brush)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            // poster and additional button
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 140.dp, height = 210.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(brush)
                                )
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(24.dp),
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .align(Alignment.CenterVertically),
                                ) {
                                    // 3 button
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .fillMaxWidth()
                                            .height(32.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(brush)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .fillMaxWidth()
                                            .height(32.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(brush)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .fillMaxWidth()
                                            .height(32.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(brush)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(brush)
                            )

                            Spacer(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = MaterialTheme.colors.onSurface)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 24.dp, bottom = 16.dp)
                                        .width(52.dp)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(brush)
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(top = 24.dp, bottom = 16.dp)
                                        .width(52.dp)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(brush)
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(top = 24.dp, bottom = 16.dp)
                                        .width(52.dp)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(brush)
                                )
                            }
                            Spacer(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = MaterialTheme.colors.onSurface)
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(brush)
                            )
                            Spacer(Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                                    .height(18.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(brush)
                            )
                            Spacer(Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(brush)
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(4.dp)
                .background(
                    MaterialTheme.colors.secondaryVariant,
                    shape = CircleShape
                )
        ) {
            IconButton(onClick = navigateBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(
                        R.string.back_button
                    ),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


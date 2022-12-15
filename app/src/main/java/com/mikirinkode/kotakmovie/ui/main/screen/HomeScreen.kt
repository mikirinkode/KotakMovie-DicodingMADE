package com.mikirinkode.kotakmovie.ui.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.utils.Constants
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.CompactMovieItem
import com.mikirinkode.kotakmovie.ui.components.TrendingMovieItem
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.HomeViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
//    navigateToDetail: (Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home))
                },
                backgroundColor = MaterialTheme.colors.secondaryVariant
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            item {
                SectionTitle(title = stringResource(R.string.trending_this_week))
                viewModel.trendingState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getTrendingThisWeekList()
                            Text(
                                text = "TRENDING LOADING",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(Color.Red)
                                    .padding(16.dp)
                            )
                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {
                                Text(
                                    text = "TRENDING EMPTY",
                                    fontSize = 22.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .background(Color.Red)
                                        .padding(16.dp)
                                )
                            } else {
                                TrendingCardRow(list = uiState.data)
                            }
                        }
                        is UiState.Error -> {
                            Text(
                                text = "TRENDING ERROR",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(Color.Red)
                                    .padding(16.dp)
                            )
                        }
                    }
                }

                SectionTitle(title = stringResource(R.string.upcoming_movies))
                viewModel.upcomingState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getUpcomingMovies()

                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {
                            } else {
                                CompactCardRow(list = uiState.data)
                            }
                        }
                        is UiState.Error -> {
                        }
                    }
                }


                SectionTitle(title = stringResource(R.string.top_rated_tv_shows))
                viewModel.topTvState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getTopTvShowList()

                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {
                            } else {
                                CompactCardRow(list = uiState.data)
                            }
                        }
                        is UiState.Error -> {
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingCardRow(
    list: List<Catalogue>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(list) { movie ->
            TrendingMovieItem(
                imageUrl = "${Constants.IMAGE_BASE_URL}${movie.posterPath}" ?: "",
                title = movie.title ?: stringResource(id = R.string.no_data),
                rating = movie.voteAverage,
                onClick = {
//                                navigateToDetail(movie.id)
                }
            )
        }
    }
}

@Composable
fun CompactCardRow(
    list: List<Catalogue>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(list) { movie ->
            CompactMovieItem(
                imageUrl = "${Constants.IMAGE_BASE_URL}${movie.posterPath}" ?: "",
                rating = movie.voteAverage
            )
        }
    }
}

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = 16.sp,
        color = Color.White,
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    KotakMovieTheme {
//        HomeScreen()
    }
}
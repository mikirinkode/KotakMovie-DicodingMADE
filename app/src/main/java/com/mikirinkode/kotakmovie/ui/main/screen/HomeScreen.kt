package com.mikirinkode.kotakmovie.ui.main.screen

import androidx.compose.foundation.layout.PaddingValues
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
import com.mikirinkode.kotakmovie.core.utils.MovieStatus
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.*
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.HomeViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String, Boolean, Int) -> Unit
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
                            ShimmerTrendingCardRow()
                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {
                                NoInternetCard(onClick = {
                                    viewModel.getTrendingThisWeekList()
                                    viewModel.getUpcomingMovies()
                                    viewModel.getTopTvShowList()
                                })
                            } else {
                                TrendingCardRow(
                                    list = uiState.data,
                                    navigateToDetail = navigateToDetail
                                )
                            }
                        }
                        is UiState.Error -> {
                            // TODO LATER

                        }
                    }
                }

                SectionTitle(title = stringResource(R.string.upcoming_movies))
                viewModel.upcomingState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getUpcomingMovies()
                            ShimmerCompactCardRow()
                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {// TODO LATER
                            } else {
                                UpcomingCompactCardRow(list = uiState.data,
                                    navigateToDetail = navigateToDetail)
                            }
                        }
                        is UiState.Error -> {// TODO LATER
                        }
                    }
                }


                SectionTitle(title = stringResource(R.string.top_rated_tv_shows))
                viewModel.topTvState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {// TODO LATER
                            viewModel.getTopTvShowList()
                            ShimmerCompactCardRow()
                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {// TODO LATER
                            } else {
                                TopCompactCardRow(list = uiState.data,
                                    navigateToDetail = navigateToDetail)
                            }
                        }
                        is UiState.Error -> {// TODO LATER
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
    navigateToDetail: (String, Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(list) { movie ->
            TrendingMovieItem(
                imageUrl = "${Constants.IMAGE_BASE_URL}${movie.backdropPath}",
                title = movie.title ?: stringResource(id = R.string.no_data),
                rating = movie.voteAverage,
                onClick = {
                    navigateToDetail(MovieStatus.TRENDING, movie.isTvShow ,movie.id)
                }
            )
        }
    }
}
@Composable
fun ShimmerTrendingCardRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        item {
            repeat(3){
                ShimmerTrendingMovieItem()
            }
        }
    }
}

@Composable
fun UpcomingCompactCardRow(
    list: List<Catalogue>,
    navigateToDetail: (String, Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(list) { movie ->
            CompactMovieItem(
                imageUrl = "${Constants.IMAGE_BASE_URL}${movie.posterPath}",
                rating = movie.voteAverage,
                onClick = {
                    navigateToDetail(MovieStatus.UPCOMING, movie.isTvShow, movie.id)
                }
            )
        }
    }
}
@Composable
fun TopCompactCardRow(
    list: List<Catalogue>,
    navigateToDetail: (String, Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(list) { movie ->
            CompactMovieItem(
                imageUrl = "${Constants.IMAGE_BASE_URL}${movie.posterPath}",
                rating = movie.voteAverage,
                onClick = {
                    navigateToDetail(MovieStatus.TOPRATED, movie.isTvShow, movie.id)
                }
            )
        }
    }
}

@Composable
fun ShimmerCompactCardRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        item {
            repeat(5){
                ShimmerCompactMovieItem()
            }
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
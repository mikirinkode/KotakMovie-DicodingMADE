package com.mikirinkode.kotakmovie.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.ui.components.CompactMovieItem
import com.mikirinkode.kotakmovie.ui.components.TrendingMovieItem
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "HOME", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun HomeContent(
    trendingList: List<Catalogue>,
    upcomingMovieList: List<Catalogue>,
    topRatedTvList: List<Catalogue>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                LazyRow(
                    modifier = Modifier,
                    contentPadding = PaddingValues(end = 16.dp)
                ) {
                    items(trendingList) { movie ->
                        TrendingMovieItem(
                            imageUrl = movie.backdropPath ?: "",
                            title = movie.title ?: "",
                            rating = movie.voteAverage,
                            onClick = {
                                navigateToDetail(movie.id)
                            }
                        )
                    }
                }

                SectionTitle(title = stringResource(R.string.upcoming_movies))
                LazyRow(
                    modifier = Modifier,
                    contentPadding = PaddingValues(end = 16.dp)
                ) {
                    items(upcomingMovieList) { movie ->
                        CompactMovieItem(
                            imageUrl = movie.posterPath ?: "",
                            rating = movie.voteAverage
                        )
                    }
                }

                SectionTitle(title = stringResource(R.string.top_rated_tv_shows))
                LazyRow(
                    modifier = Modifier,
                    contentPadding = PaddingValues(end = 16.dp)
                ) {
                    items(topRatedTvList) { movie ->
                        CompactMovieItem(
                            imageUrl = movie.posterPath ?: "",
                            rating = movie.voteAverage
                        )
                    }
                }
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

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF0E1621)
@Composable
fun HomeScreenPreview() {
    KotakMovieTheme {
//        HomeScreen(navigateToDetail = {})
    }
}
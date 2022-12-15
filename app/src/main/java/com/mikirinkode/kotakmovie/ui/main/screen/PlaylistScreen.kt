package com.mikirinkode.kotakmovie.ui.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.R
import kotlinx.coroutines.launch


@Composable
fun PlaylistScreen(
    navigateToDetail: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.menu_playlist))
                },
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        TabLayout(navigateToDetail = navigateToDetail, modifier = Modifier.padding(innerPadding), )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    navigateToDetail: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = 2)
    Column() {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, navigateToDetail = navigateToDetail)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Movie" to R.drawable.ic_movie_unselected,
        "TV Show" to R.drawable.ic_tv_unselected
    )

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = MaterialTheme.colors.primary
            )
        }
    ) {
        list.forEachIndexed { index, s ->
            Tab(
                icon = {
                    Icon(
                        painter = painterResource(id = list[index].second),
                        tint = if (pagerState.currentPage == index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                },
                text = {
                    Text(
                        text = list[index].first,
                        color = if (pagerState.currentPage == index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    navigateToDetail: (Boolean, Int) -> Unit,
) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> MoviePlaylistScreen(navigateToDetail)
            1 -> TvShowPlaylistScreen(navigateToDetail)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlaylistScreenPreview() {
    KotakMovieTheme {
//        PlaylistScreen()
    }
}
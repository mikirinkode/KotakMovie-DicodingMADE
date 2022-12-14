package com.mikirinkode.kotakmovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mikirinkode.kotakmovie.ui.main.screen.HomeScreen
import com.mikirinkode.kotakmovie.ui.main.screen.SearchScreen
import com.mikirinkode.kotakmovie.ui.navigation.NavigationItem
import com.mikirinkode.kotakmovie.ui.navigation.Screen
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun KotakMovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
//                    navigateToDetail = { movieId ->
//                        navController.navigate(Screen.DetailMovie.createRoute(movieId))
//                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen()
            }
            composable(Screen.Movie.route) {
                HomeScreen()
            }
            composable(Screen.TvShow.route) {
                SearchScreen()
            }
            composable(Screen.Playlist.route) {
                HomeScreen()
            }

//            composable(
//                route = Screen.DetailMovie.route,
//                arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
//            ) {
//                val id = it.arguments?.getInt("movieId") ?: 1
//                DetailScreen(
//                    movieId = id,
//                    navigateBack = {
//                        navController.navigateUp()
//                    },
//                    navigateToTrailerScreen = {
//
//                    }
//                )
//            }
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home),
            iconSelected = painterResource(id = R.drawable.ic_home_selected),
            iconUnselected = painterResource(id = R.drawable.ic_home_unselected),
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.search),
            iconSelected = painterResource(id = R.drawable.ic_search_selected),
            iconUnselected = painterResource(id = R.drawable.ic_search_unselected),
            screen = Screen.Search
        ),
        NavigationItem(
            title = stringResource(R.string.movie),
            iconSelected = painterResource(id = R.drawable.ic_movie_selected),
            iconUnselected = painterResource(id = R.drawable.ic_movie_unselected),
            screen = Screen.Movie
        ),
        NavigationItem(
            title = stringResource(R.string.tv_show),
            iconSelected = painterResource(id = R.drawable.ic_tv_selected),
            iconUnselected = painterResource(id = R.drawable.ic_tv_unselected),
            screen = Screen.TvShow
        ),
        NavigationItem(
            title = stringResource(R.string.playlist),
            iconSelected = painterResource(id = R.drawable.ic_playlist_selected),
            iconUnselected = painterResource(id = R.drawable.ic_playlist_unselected),
            screen = Screen.Playlist
        ),

        )

    BottomNavigation(
        modifier = modifier
    ) {
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = if (currentRoute == item.screen.route) item.iconSelected else item.iconUnselected,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    alwaysShowLabel = currentRoute == item.screen.route,
                    label = {
                        Text(
                            text = item.title,
                            maxLines = 1
                        )
                    },
                    selected = currentRoute == item.screen.route,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onBackground,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.secondaryVariant)
                )
            }
        }
    }
}

@Preview
@Composable
fun KotakMovieAppPreview() {
    KotakMovieTheme {
        KotakMovieApp()
    }
}
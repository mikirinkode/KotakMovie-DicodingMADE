package com.mikirinkode.kotakmovie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mikirinkode.kotakmovie.ui.main.screen.*
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
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Search.route || currentRoute == Screen.Movie.route || currentRoute == Screen.TvShow.route|| currentRoute == Screen.Playlist.route) {
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
            // route: /home
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { isTvShow, movieId ->
                        navController.navigate(
                            Screen.DetailMovie.createRoute(
                                isTvShow = isTvShow,
                                movieId = movieId
                            )
                        )
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    navigateToDetail = { isTvShow, movieId ->
                        navController.navigate(
                            Screen.DetailMovie.createRoute(
                                isTvShow = isTvShow,
                                movieId = movieId
                            )
                        )
                    })
            }
            composable(Screen.Movie.route) {
                MovieListScreen(
                    navigateToDetail = { isTvShow, movieId ->
                        navController.navigate(
                            Screen.DetailMovie.createRoute(
                                isTvShow = isTvShow,
                                movieId = movieId
                            )
                        )
                    })
            }
            composable(Screen.TvShow.route) {
                TvShowListScreen(
                    navigateToDetail = { isTvShow, movieId ->
                        navController.navigate(
                            Screen.DetailMovie.createRoute(
                                isTvShow = isTvShow,
                                movieId = movieId
                            )
                        )
                    })
            }
            composable(Screen.Playlist.route) {
                PlaylistScreen(
                    navigateToDetail = { isTvShow, movieId ->
                        navController.navigate(
                            Screen.DetailMovie.createRoute(
                                isTvShow = isTvShow,
                                movieId = movieId
                            )
                        )
                    })
            }

            //route: /home/{isTvShow}/{movieId}/detail
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(
                    navArgument("isTvShow") { type = NavType.BoolType },
                    navArgument("movieId") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("movieId") ?: 1
                val isTvShow = it.arguments?.getBoolean("isTvShow") ?: false
                val context = LocalContext.current

                DetailScreen(
                    movieId = id,
                    isTvShow = isTvShow,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToTrailerScreen = { videoKey ->
                        openYoutube(context, videoKey)
                    },
                    onShareButtonClicked = { title ->
                        shareMovie(context, title)
                    }
                )
            }

            //route: /home/{isTvShow}/{movieId}/detail/trailer
//            composable(
//                route = Screen.Trailer.route,
//                arguments = listOf(
//                    navArgument("isTvShow") { type = NavType.BoolType },
//                    navArgument("movieId") { type = NavType.IntType }),
//            ) {
//                val id = it.arguments?.getInt("movieId") ?: 1
//                val isTvShow = it.arguments?.getBoolean("isTvShow") ?: false
//                TrailerScreen(
//                    movieId = id,
//                    isTvShow = isTvShow,
//                    navigateUp = {
//                        navController.navigateUp()
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

private fun shareMovie(context: Context, movieTitle: String) {
    val shareIntent = Intent()
    val appName = context.getString(R.string.app_name)
    val appPlayStoreLink = context.getString(R.string.app_link)
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_TEXT, "Watch $movieTitle on $appName \n$appPlayStoreLink")
    shareIntent.type = "text/plain"
    context.startActivity(
        Intent.createChooser(
            shareIntent,
            context.getString(R.string.share_to)
        )
    )
}
private fun openYoutube(context: Context, videoKey: String) {
    if (videoKey == ""){
        Toast.makeText(context, "-No Data Available-", Toast.LENGTH_SHORT).show()
    } else {
    val link = "https://youtu.be/$videoKey"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.setPackage("com.google.android.youtube")
    context.startActivity(intent)
    }
}

@Preview
@Composable
fun KotakMovieAppPreview() {
    KotakMovieTheme {
        KotakMovieApp()
    }
}
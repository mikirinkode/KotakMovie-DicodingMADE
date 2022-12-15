package com.mikirinkode.kotakmovie.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Search: Screen("search")
    object Movie: Screen("movie")
    object TvShow: Screen("tvshow")
    object Playlist: Screen("playlist")
    object DetailMovie: Screen("home/{isTvShow}/{movieId}/detail") {
        fun createRoute(isTvShow: Boolean, movieId: Int) = "home/$isTvShow/$movieId/detail"
    }
    object Trailer: Screen("home/{isTvShow}/{movieId}/detail/trailer"){
        fun createRoute(isTvShow: Boolean, movieId: Int) = "home/$isTvShow/$movieId/detail/trailer"
    }
}
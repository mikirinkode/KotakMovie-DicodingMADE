package com.mikirinkode.kotakmovie.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Search: Screen("search")
    object Movie: Screen("movie")
    object TvShow: Screen("tvshow")
    object Playlist: Screen("playlist")
    object DetailMovie: Screen("home/{status}/{isTvShow}/{movieId}/detail") {
        fun createRoute(status: String, isTvShow: Boolean, movieId: Int) = "home/$status/$isTvShow/$movieId/detail"
    }
    object Trailer: Screen("home/{status}/{isTvShow}/{movieId}/detail/trailer"){
        fun createRoute(status: String, isTvShow: Boolean, movieId: Int) = "home/$status/$isTvShow/$movieId/detail/trailer"
    }
}
package com.mikirinkode.kotakmovie.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.MovieListComponent
import com.mikirinkode.kotakmovie.ui.components.ShimmerMovieListComponent
import com.mikirinkode.kotakmovie.ui.components.StateMessageComponent
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.TvShowPlaylistViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory

@Composable
fun TvShowPlaylistScreen(
    navigateToDetail: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: TvShowPlaylistViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))

    Box(modifier = modifier.fillMaxSize()) {
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState){
                is UiState.Loading -> {
                    viewModel.getTvShowPlaylist()
                    ShimmerMovieListComponent()
                }
                is UiState.Success -> {
                    if (uiState.data.isEmpty()){
                        StateMessageComponent(
                            drawableId = R.drawable.ic_empty_playlist_illustration,
                            drawableDesc = R.string.empty_playlist_illustration,
                            imageWidth = 200,
                            imageHeight = 250,
                            titleStringId = R.string.empty_playlist_title,
                            descriptionStringId = R.string.empty_playlist_description,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        MovieListComponent(list = uiState.data, navigateToDetail = navigateToDetail)
                    }
                }
                is UiState.Error -> {
                    StateMessageComponent(
                        drawableId = R.drawable.ic_error_state,
                        drawableDesc = R.string.error_illustration,
                        imageWidth = 187,
                        imageHeight = 178,
                        titleStringId = R.string.error_title,
                        descriptionStringId = R.string.error_desc,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TvShowPlaylistScreenPreview() {
    KotakMovieTheme {
//        TvShowPlaylistScreen()
    }
}
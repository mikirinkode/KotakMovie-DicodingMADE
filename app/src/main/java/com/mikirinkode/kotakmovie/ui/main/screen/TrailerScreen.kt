package com.mikirinkode.kotakmovie.ui.main.screen

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.ShimmerTrailer
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.TrailerViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun TrailerScreen(
    movieId: Int,
    isTvShow: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val context = LocalContext.current
    val viewModel: TrailerViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Clips")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(
                                id = R.string.back_button
                            ),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        if (isTvShow) {
                            viewModel.getTvShowTrailer(movieId)
                        } else {
                            viewModel.getMovieTrailer(movieId)
                        }
                        LazyColumn(){
                            item {
                                repeat(3){
                                    ShimmerTrailer()
                                }
                            }
                        }
                    }
                    is UiState.Success -> {
                        if (uiState.data.isEmpty()) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .padding(24.dp)
                                    .align(Alignment.Center)
                                    .background(MaterialTheme.colors.secondaryVariant)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "No clip available for this movie at this moment.",
                                    fontSize = 18.sp
                                )
                            }
                        } else {

                            LazyColumn(modifier = Modifier) {
                                items(uiState.data) { video ->
                                    VideoScreen(
                                        lifecycle = lifecycle,
                                    trailerVideoKey = video.key)
                                    Spacer(modifier = Modifier.height(64.dp))
                                }
                            }
                        }
                    }
                    is UiState.Error -> {
                        // TODO LATER
                    }
                }
            }

        }
    }
}

@Composable
fun VideoScreen(
    lifecycle: Lifecycle,
    trailerVideoKey: String,
) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.youtube_player_popup, null)

        },
        modifier = Modifier.wrapContentSize(),
        update = {

            val youTubePlayerView =
                it.findViewById<YouTubePlayerView>(R.id.you_tube_player_view)
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.enableAutomaticInitialization = true
            youTubePlayerView.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.cueVideo(trailerVideoKey, 0f)
                    }
                }
            )
//            val options = IFramePlayerOptions.Builder().controls(0).build()
//            youTubePlayerView.initialize(listener, options)
        }
    )
}

@Preview
@Composable
fun TrailerScreenPreview() {
    KotakMovieTheme {
        TrailerScreen(0, false, {})
    }
}
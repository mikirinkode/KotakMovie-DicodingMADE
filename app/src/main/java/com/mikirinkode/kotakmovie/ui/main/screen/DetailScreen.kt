package com.mikirinkode.kotakmovie.ui.main.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.utils.Constants
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.ShimmerDetailScreen
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.DetailMovieViewModel
import com.mikirinkode.kotakmovie.viewmodel.TrailerViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DetailScreen(
    movieId: Int,
    isTvShow: Boolean,
    navigateBack: () -> Unit,
    navigateToTrailerScreen: (String) -> Unit,
    onShareButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(
        0f to MaterialTheme.colors.background,
        500f to Color.Transparent,
        1000f to MaterialTheme.colors.background
    )

    val context = LocalContext.current
    val viewModel: DetailMovieViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))
    val trailerViewModel: TrailerViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))

    var trailerKey by remember {
        mutableStateOf("")
    }

    trailerViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                if (isTvShow) {
                    trailerViewModel.getTvShowTrailer(movieId)
                } else {
                    trailerViewModel.getMovieTrailer(movieId)
                }
            }
            is UiState.Success -> {
                if (uiState.data.isNotEmpty()){
                    trailerKey = uiState.data[0].key
                }
            }
            is UiState.Error -> {}
        }
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                if (isTvShow) {
                    viewModel.getTvShowDetail(tvShowId = movieId)
                } else {
                    viewModel.getMovieDetail(movieId = movieId)
                }
                ShimmerDetailScreen(
                    navigateBack = navigateBack
                )
            }
            is UiState.Success -> {
                val data = uiState.data

                DetailMovieContent(
                    id = data.id,
                    isTvShow = data.isTvShow,
                    title = data.title ?: stringResource(id = R.string.no_data),
                    posterUrl = "${Constants.IMAGE_BASE_URL}${data.posterPath}",
                    backdropUrl = "${Constants.IMAGE_BASE_URL}${data.backdropPath}",
                    genre = data.genres ?: stringResource(id = R.string.no_genre_data),
                    runtime = data.runtime ?: 0,
                    voteAverage = data.voteAverage,
                    releaseDate = data.releaseDate ?: stringResource(id = R.string.no_data),
                    tagline = "\"${data.tagline}\"",
                    overview = data.overview ?: stringResource(id = R.string.no_data),
                    isOnPlaylist = data.isOnPlaylist,
                    navigateBack = navigateBack,
                    navigateToTrailerScreen = { navigateToTrailerScreen(trailerKey) },
                    onPlaylistIconClicked = { addToPlaylist ->
                        if (addToPlaylist) {
                            if (isTvShow) {
                                viewModel.insertTvShowToPlaylist(data, addToPlaylist)
                            } else {
                                viewModel.insertMovieToPlaylist(data, addToPlaylist)
                            }
                            Toast.makeText(
                                context,
                                context.getString(R.string.added_to_playlist),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (isTvShow) {
                                viewModel.removeTvShowFromPlaylist(data)
                            } else {
                                viewModel.removeMovieFromPlaylist(data)
                            }
                            Toast.makeText(
                                context,
                                context.getString(R.string.removed_from_playlist),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onShareButtonClicked = onShareButtonClicked
                )
            }
            is UiState.Error -> {
                //TODO LATER
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                ) {
                    Text(text = "ERROR", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}

@Composable
fun DetailMovieContent(
    id: Int,
    isTvShow: Boolean,
    title: String,
    posterUrl: String,
    backdropUrl: String,
    genre: String,
    runtime: Int,
    voteAverage: Double,
    releaseDate: String,
    tagline: String,
    overview: String,
    isOnPlaylist: Boolean,
    navigateBack: () -> Unit,
    navigateToTrailerScreen: (String) -> Unit,
    onPlaylistIconClicked: (newState: Boolean) -> Unit,
    onShareButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = rememberCollapsingToolbarScaffoldState()
    var isAddedPlaylist by rememberSaveable {
        mutableStateOf(isOnPlaylist)
    }
    val df = DecimalFormat("#.#")
    val formattedVote = df.format(voteAverage)
    var dateFormatted = ""

    val duration = if (!isTvShow){
        val hours = runtime.div(60)
        val minutes = runtime.rem(60)
        "${hours}h ${minutes}m"
    } else {
        "${runtime}min"
    }

    if (releaseDate != ""){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = releaseDate.let { dateFormat.parse(it) }
        if (date != null) {
            dateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date)
        }

    }

    Box {
        CollapsingToolbarScaffold(
            modifier = modifier.fillMaxSize(),
            state = state,
            toolbarModifier = Modifier.background(MaterialTheme.colors.secondaryVariant),
            enabled = true,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                AsyncImage(
                    model = backdropUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .parallax(0.5f)
                        .fillMaxWidth()
                        .height(200.dp)
                        .graphicsLayer {
                            // change alpha of image as the toolbar expands
                            alpha = state.toolbarState.progress
                        }
                )

                Box(
                    Modifier
                        .parallax(0.5f)
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colors.background,
                                    Color.Transparent,
                                    MaterialTheme.colors.background
                                )
                            )
                        )
                )
            }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    // detail movie card

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = MaterialTheme.colors.secondaryVariant,
                        modifier = modifier
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage( // TODO LOADING IMAGE
                                    model = "${Constants.IMAGE_BASE_URL}$posterUrl",
                                    contentDescription = stringResource(id = R.string.film_poster),
                                    modifier = Modifier
                                        .size(width = 140.dp, height = 210.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(color = MaterialTheme.colors.background)
                                )
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .align(Alignment.CenterVertically),
                                ) {
                                    // 3 button
                                    DetailAdditionalButton(
                                        iconId = R.drawable.ic_play_trailer,
                                        iconDesc = stringResource(R.string.play_trailer_button),
                                        buttonText = stringResource(R.string.play_trailer),
                                        onClick = {
                                            navigateToTrailerScreen("")
                                        })
                                    DetailAdditionalButton(
                                        iconId = if (isAddedPlaylist) R.drawable.ic_added_to_playlist else R.drawable.ic_add_to_playlist,
                                        iconDesc = stringResource(R.string.add_to_playlist_button),
                                        buttonText = if (isAddedPlaylist) stringResource(R.string.on_playlist) else stringResource(
                                            R.string.add_to_playlist
                                        ),
                                        onClick = {
                                            isAddedPlaylist = !isAddedPlaylist
                                            onPlaylistIconClicked(isAddedPlaylist)
                                        }
                                    )
                                    DetailAdditionalButton(
                                        iconId = R.drawable.ic_share,
                                        iconDesc = stringResource(R.string.share_button),
                                        buttonText = stringResource(R.string.share),
                                        onClick = {
                                            onShareButtonClicked(title)
                                        })
                                }
                            }
                            Text(
                                text = genre,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
                            )
                            Spacer(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = MaterialTheme.colors.onSurface)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                DetailInfoItem(R.drawable.ic_time, "runtime", duration)
                                DetailInfoItem(
                                    R.drawable.ic_star_outlined,
                                    "vote average",
                                    formattedVote.toString()
                                )
                                DetailInfoItem(R.drawable.ic_date, "release date", dateFormatted)
                            }
                            Spacer(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = MaterialTheme.colors.onSurface)
                            )
                            Text(
                                text = tagline,
                                color = MaterialTheme.colors.onSurface,
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.overview),
                                color = Color.White,
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = overview,
                                color = MaterialTheme.colors.onSurface,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(4.dp)
                .background(
                    MaterialTheme.colors.secondaryVariant,
                    shape = CircleShape
                )
        ) {
            IconButton(onClick = navigateBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(
                        R.string.back_button
                    ),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun DetailInfoItem(
    iconId: Int,
    iconDesc: String,
    data: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = iconDesc,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 4.dp)
        )
        Text(text = data, fontSize = 14.sp)
    }
}

@Composable
fun DetailAdditionalButton(
    iconId: Int,
    iconDesc: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = true,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            tint = MaterialTheme.colors.primary,
            contentDescription = iconDesc,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = buttonText,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    KotakMovieTheme {
//        DetailMovieContent()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    KotakMovieTheme {
//        DetailScreen(1, false, {}, {1; false}, {})
    }
}
package com.mikirinkode.kotakmovie.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.utils.SortUtils
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.MovieListComponent
import com.mikirinkode.kotakmovie.ui.components.NoInternetCard
import com.mikirinkode.kotakmovie.ui.components.ShimmerMovieListComponent
import com.mikirinkode.kotakmovie.ui.components.StateMessageComponent
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.MovieListViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory

@Composable
fun MovieListScreen(
    navigateToDetail: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember {
        mutableStateOf(false)
    }

    var selected by remember {
        mutableStateOf(SortUtils.POPULAR)
    }

    val dropdownMenuItems =
        listOf(
            SortUtils.POPULAR,
            SortUtils.LATEST,
            SortUtils.OLDEST,
            SortUtils.BEST,
            SortUtils.RANDOM
        )

    val context = LocalContext.current
    val viewModel: MovieListViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))


    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.movie))
            },
            actions = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Menu"
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier
                        .align(Alignment.Bottom)
                ) {
                    for (item in dropdownMenuItems) {
                        MyDropDownItem(selected = selected, title = item, onClick = {
                            selected = item
                            showMenu = false
                            viewModel.getPopularMoviesList(selected, false)
                        })
                    }
                }
            },
            backgroundColor = MaterialTheme.colors.secondaryVariant
        )

        Box(modifier = Modifier.fillMaxSize()){
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getPopularMoviesList(selected, false)
                        ShimmerMovieListComponent()
                    }
                    is UiState.Success -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (uiState.data.isEmpty()) {
                                NoInternetCard(onClick = {
                                    viewModel.getPopularMoviesList(selected, false)
                                })
                            } else {
                                MovieListComponent(list = uiState.data, navigateToDetail = navigateToDetail)
                            }
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
}


@Composable
fun MyDropDownItem(
    selected: String,
    title: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(onClick = onClick) {
        Text(
            text = title,
            color = if (selected == title) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
        )
    }
}


@Preview
@Composable
fun MovieListScreenPreview() {
    KotakMovieTheme {
//        MovieListScreen(listOf())
    }
}
package com.mikirinkode.kotakmovie.ui.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.di.Injection
import com.mikirinkode.kotakmovie.ui.common.UiState
import com.mikirinkode.kotakmovie.ui.components.MovieListComponent
import com.mikirinkode.kotakmovie.ui.components.StateMessageComponent
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.viewmodel.MovieListViewModel
import com.mikirinkode.kotakmovie.viewmodel.SearchViewModel
import com.mikirinkode.kotakmovie.viewmodel.ViewModelFactory

@Composable
fun SearchScreen(
    navigateToDetail: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: SearchViewModel =
        viewModel(factory = ViewModelFactory(Injection.provideRepository(context)))

    val query by viewModel.query

    Column {
        SearchBar(
            inputValue = query,
            onValueChange = viewModel::searchMovies,
            onClickTrailingIcon = viewModel::clearQuery
        )

        Box(modifier = Modifier.fillMaxSize()){
            if (query.isEmpty()){
                StateMessageComponent(
                    drawableId = R.drawable.ic_initial_search_state_illustration,
                    drawableDesc = R.string.initial_state_illustration,
                    imageWidth = 300,
                    imageHeight = 150,
                    titleStringId = R.string.initial_search_title,
                    descriptionStringId = R.string.initial_search_desc,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState){
                        is UiState.Loading -> {
                            // TODO LATER
                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {
                                StateMessageComponent(
                                    drawableId = R.drawable.ic_search_empty_result_illustration,
                                    drawableDesc = R.string.empty_search_result_illustration,
                                    imageWidth = 200,
                                    imageHeight = 195,
                                    titleStringId = R.string.empty_search_result_title,
                                    descriptionStringId = R.string.empty_search_result_desc,
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
    }
}

@Composable
fun SearchBar(
    inputValue: String,
    onValueChange: (String) -> Unit,
    onClickTrailingIcon: () -> Unit,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }
    TextField(
        value = inputValue,
        textStyle = TextStyle(
            color = Color.White
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (inputValue.isNotEmpty()) {
                IconButton(onClick = onClickTrailingIcon) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colors.onSurface,
                        contentDescription = "Close Button",
                    )
                }
            }
        },
        placeholder = {
            Text(text = "Search")
        },
        onValueChange = onValueChange,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
            .background(MaterialTheme.colors.secondaryVariant)
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions()
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


@Preview
@Composable
fun SearchScreenPreview() {
    KotakMovieTheme {
//        SearchScreen({})
    }
}
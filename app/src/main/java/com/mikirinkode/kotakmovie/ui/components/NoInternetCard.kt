package com.mikirinkode.kotakmovie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme

@Composable
fun NoInternetCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_internet),
                contentDescription = stringResource(R.string.no_internet_illustration),
                modifier = Modifier.size(width = 185.dp, height = 150.dp)
            )
            Text(
                text = stringResource(R.string.no_internet_title),
                fontSize = 24.sp,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            Text(
                text = stringResource(R.string.no_internet_description),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
            )
            Button(onClick = onClick, modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.try_again), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NoInternetCardPreview() {
    KotakMovieTheme {
        NoInternetCard(onClick = {})
    }
}
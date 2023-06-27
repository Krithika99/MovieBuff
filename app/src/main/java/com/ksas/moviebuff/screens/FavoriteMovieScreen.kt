package com.ksas.moviebuff.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import coil.compose.AsyncImage
import com.ksas.moviebuff.R
import com.ksas.moviebuff.viewmodel.MoviesDatabaseViewModel


@Composable
fun FavMovieScreen(
    viewModel: MoviesDatabaseViewModel,
    context: Context,
    screenState: FavoriteScreenStateHolder
) {
    viewModel.getAllMovies()
    val getAllMoviesLivedata =
        viewModel.getAllMoviesLivedata.asFlow().collectAsState(initial = emptyList())
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        items(getAllMoviesLivedata.value) {
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(5.dp, shape = RoundedCornerShape(8.dp))
                    .height(200.dp)
                    .width(150.dp)
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = if (it.url == "") R.drawable.noimage else it.url,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = it.name,
                        modifier = Modifier,
                        fontSize = 12.sp,
                        fontFamily = customFontMainHeading,
                    )
                }
            }
        }
    }
}
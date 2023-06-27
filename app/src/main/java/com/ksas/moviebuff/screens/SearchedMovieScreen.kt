package com.ksas.moviebuff.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.ksas.moviebuff.MovieDetailScreen
import com.ksas.moviebuff.R
import com.ksas.moviebuff.titleId
import com.ksas.moviebuff.viewmodel.MoviesViewModel


@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun SearchedMovieScreen(viewModel: MoviesViewModel, navController: NavHostController) {
    var searchText by remember {
        mutableStateOf("")
    }
    val moviePagerData = viewModel.searchMovieByTitle(searchText).collectAsLazyPagingItems()

    Column(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.searchMovieByTitle(searchText)
            },
            placeholder = {
                Text(
                    "Search...",
                    color = MaterialTheme.colors.primary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
                .padding(horizontal = 8.dp),
            textStyle = TextStyle(color = Color.Black),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {

            })
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            items(moviePagerData.itemSnapshotList) {
                Row {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(5.dp)
                    ) {
                        AsyncImage(
                            model = it?.primaryImage?.url ?: R.drawable.noimage,
                            contentDescription = "image",
                            modifier = Modifier
                                .height(200.dp)
                                .width(300.dp),
                            contentScale = ContentScale.Fit,
                        )

                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    ) {
                        Text(text = "Title: ${it?.titleText?.text}")
                        Text(text = "Release year: ${it?.releaseYear?.year.toString()}")
                        Text(text = "Series: ${if (it?.titleType?.isSeries == true) "Yes" else "No"}")
                        Text(text = "Episodes: ${if (it?.titleType?.canHaveEpisodes == true) "Yes" else "No"}")
                        it?.titleType?.categories?.forEach { category ->
                            val categoryText = category.value
                            Text(text = "Category: $categoryText")
                            Log.d(TAG, categoryText)
                        }
                    }

                }

            }
        }
    }
}
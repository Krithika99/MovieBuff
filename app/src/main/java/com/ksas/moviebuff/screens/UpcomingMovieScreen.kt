package com.ksas.moviebuff.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.ksas.moviebuff.MovieDetailScreen
import com.ksas.moviebuff.R
import com.ksas.moviebuff.UpcomingMoviesScreen
import com.ksas.moviebuff.database.Movie
import com.ksas.moviebuff.viewmodel.MoviesDatabaseViewModel
import com.ksas.moviebuff.viewmodel.MoviesViewModel
import java.lang.Exception

@Composable
fun UpcomingMovies(viewModel: MoviesViewModel, navController: NavHostController) {

    val upcomingMoviesLivedata = viewModel.upcomingMovies.asFlow().collectAsState(initial = null)
    viewModel.upcomingMoviesWithoutPaging()
    Log.d(TAG, "KTEST : ${upcomingMoviesLivedata.value?.results}")
    Column(
        modifier = Modifier
            .padding(start = 4.dp)
            .background(color = MaterialTheme.colors.background)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.Start
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Upcoming movies",
                modifier = Modifier.padding(start = 5.dp),
                fontFamily = customFontSubHeading,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier.clickable {
                    navController.navigate(UpcomingMoviesScreen)
                }, tint = MaterialTheme.colors.onSurface
            )
        }


        LazyRow {
            try {
                upcomingMoviesLivedata.value?.let {
                    items(it.results) {
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .shadow(5.dp, shape = RoundedCornerShape(8.dp))
                                .height(250.dp)
                                .width(150.dp)
                                .clickable {
                                    navController.navigate("$MovieDetailScreen/${it.id}")
                                }
                        ) {
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AsyncImage(
                                    model = it?.primaryImage?.url ?: R.drawable.noimage,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                                    contentScale = ContentScale.FillBounds,
                                )
                                Text(
                                    text = "${it?.titleText?.text}",
                                    modifier = Modifier,
                                    fontSize = 12.sp,
                                    fontFamily = customFontMainHeading,
                                )
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error : ${e.message}")

            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UpcomingMoviesList(
    viewModel: MoviesViewModel,
    context: Context,
    screenState: FavoriteScreenStateHolder,
    moviesViewmodel: MoviesDatabaseViewModel
) {
    val upcomingMovies = viewModel.upcomingMovies().collectAsLazyPagingItems()
    val loadLiveData = viewModel.isLoading.asFlow().collectAsState(initial = false)

    Log.d(TAG, "KTEST : ")
    if (loadLiveData.value) {
        circularProgressIndicator()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            items(upcomingMovies.itemSnapshotList) {
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
                            model = it?.primaryImage?.url ?: R.drawable.noimage,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "${it?.titleText?.text}",
                            modifier = Modifier,
                            fontSize = 12.sp,
                            fontFamily = customFontMainHeading,
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val isFavorite = it?.let { it1 -> screenState.isFavorite(it1.id) }
                        val heartColor = if (isFavorite == true) Color.Red else Color.Gray
                        IconButton(
                            onClick = {
                                if (it != null) {
                                    screenState.toggleFavorite(it.id)
                                    val movie = it.titleText?.let { it1 ->
                                        it.releaseYear?.let { it2 ->
                                            it.releaseDate?.let { it3 ->
                                                Movie(
                                                    it.id,
                                                    it1.text,
                                                    it?.primaryImage?.url ?: "",
                                                    it2.year,
                                                    it3.day
                                                )
                                            }
                                        }
                                    }
                                    if (screenState.isFavorite(it.id)) {
                                        if (movie != null) {
                                            moviesViewmodel.insertMovies(movie)
                                        }
                                    } else {
                                        if (movie != null) {
                                            moviesViewmodel.deleteMovies(movie)
                                        }
                                    }
                                }
                            }, modifier = Modifier.size(25.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Favorite",
                                tint = heartColor
                            )
                        }
                    }
                }
            }
        }
    }
}

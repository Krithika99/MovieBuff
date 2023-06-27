package com.ksas.moviebuff.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import coil.compose.AsyncImage
import com.ksas.moviebuff.R
import com.ksas.moviebuff.viewmodel.MoviesViewModel


@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
fun ShowMovieDetails(titleId: String, viewModel: MoviesViewModel) {
    Log.d(TAG, "Card results $titleId")
    viewModel.searchMovieById(titleId)
    val movieById by viewModel.searchMovieIdResponse.asFlow().collectAsState(initial = null)
    val ratingsById by viewModel.ratingsByIdResponse.asFlow().collectAsState(initial = null)
    val isLoading by viewModel.isLoadingTest.asFlow().collectAsState(initial = false)


    val shareLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Movie Recommendation")
        putExtra(
            Intent.EXTRA_TEXT,
            "${movieById?.results?.titleText?.text}\n\n${movieById?.results?.releaseYear?.year}" +
                    "\n\n${movieById?.results?.primaryImage?.caption?.plainText}"
        )
    }
    val chooserIntent = Intent.createChooser(shareIntent, "Share Movie Details")


    if (isLoading || movieById == null) {
        Log.d(TAG, "isLoading: $isLoading")
        circularProgressIndicator()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Column(modifier = Modifier.padding(3.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(280.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                ) {
                    Box() {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AsyncImage(
                                model = movieById?.results?.primaryImage?.url ?: R.drawable.noimage,
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = movieById?.results?.titleText?.text.toString(),
                                modifier = Modifier,
                                fontSize = 18.sp,
                                fontFamily = customFontMainHeading
                            )
                        }
                        IconButton(
                            onClick = { shareLauncher.launch(chooserIntent) },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(25.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.shareicon),
                                contentDescription = "Share icon"
                            )

                        }
                    }
                }
                val colors = listOf(Color(0xFFDEF123), Color(0xFF789ABC), Color(0xFF123456))
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(modifier = Modifier.height(85.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(colors))
                        ) {
                            Text(
                                text = "Release :  ${movieById?.results?.releaseYear?.year} ",
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 25.sp,
                                fontFamily = customFontText,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(modifier = Modifier.height(85.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(colors))
                        ) {
                            Text(
                                text = "Series: ${if (movieById?.results?.titleType?.isSeries == true) "Yes" else "No"}",
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 25.sp,
                                fontFamily = customFontText,
                                color = MaterialTheme.colors.onPrimary
                            )


                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(modifier = Modifier.height(85.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(colors))
                        ) {

                            Text(
                                text = "Episodes : ${if (movieById?.results?.titleType?.isEpisode == true) "Yes" else "No"}",
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 25.sp,
                                fontFamily = customFontText,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(modifier = Modifier.height(85.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(colors))
                        ) {
                            Text(
                                text = "Ratings : ${if (ratingsById?.results?.averageRating == null) "No Ratings" else ratingsById?.results?.averageRating}",
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 25.sp,
                                fontFamily = customFontText,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(modifier = Modifier.height(85.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(colors))
                        ) {

                            Text(
                                text = "Number of votes : ${if (ratingsById?.results?.numVotes == null) "No Votes" else ratingsById?.results?.numVotes}",
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 25.sp,
                                fontFamily = customFontText,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}



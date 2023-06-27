package com.ksas.moviebuff

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import com.ksas.moviebuff.api.generic.Responses
import com.ksas.moviebuff.api.generic.Results
import com.ksas.moviebuff.screens.HomePager
import com.ksas.moviebuff.screens.UpcomingMovies
import com.ksas.moviebuff.screens.circularProgressIndicator
import com.ksas.moviebuff.ui.theme.MovieBuffTheme
import com.ksas.moviebuff.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieBuffTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState", "CheckResult")
@Composable
fun Greeting(viewModel: MoviesViewModel, navController: NavHostController) {
    /**
     * Using hiltViewModel() inside AppNavigator and passing viewModel instance to Greeting
     */
    //val viewModel: MoviesViewModel = viewModel()

    val isLoading by viewModel.isLoading.asFlow().collectAsState(initial = false)
    val titleResponse by viewModel.getTitleLiveData.asFlow().collectAsState(
        initial = Responses(
            0, "", 0,
            emptyList()
        )
    )

    var resultFromCard by remember {
        mutableStateOf(Results("1", null, null, null, null, null))
    }
    Log.d("TAG", titleResponse.toString())
    if (isLoading) {
        circularProgressIndicator()
    } else {
        Column {

            /**
             * Home screen horizontal viewpager
             */
            /**
             * Home screen horizontal viewpager
             */
            HomePager(titleResponse = titleResponse, navController, onCardClicked = {
                resultFromCard = it
                val titleId = it.id
                navController.navigate("$MovieDetailScreen/${titleId}")
            })
            /**
             * Upcoming movies
             */
            /**
             * Upcoming movies
             */
            UpcomingMovies(viewModel, navController)
        }
    }
}

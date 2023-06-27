package com.ksas.moviebuff

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ksas.moviebuff.screens.*
import com.ksas.moviebuff.viewmodel.MoviesDatabaseViewModel
import com.ksas.moviebuff.viewmodel.MoviesViewModel

const val homeRoute = "home_page"
const val MovieDetailScreen = "MovieDetailScreen"
const val SearchMovieDetailScreen = "SearchMovieDetailScreen"
const val FavMovieDetailScreen = "FavMovieDetailScreen"
const val titleId = "tt"
const val UpcomingMoviesScreen = "UpcomingMoviesScreen"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MoviesViewModel>()
    val moviesViewmodel = hiltViewModel<MoviesDatabaseViewModel>()
    val context = LocalContext.current
    Scaffold(bottomBar = {
        BottomNavigation(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.background
                )
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier,

                        )
                },
                label = {
                    Text(
                        "Home",
                        modifier = Modifier
                    )
                },
                selected = currentRoute == homeRoute,
                onClick = {
                    navController.navigate(homeRoute) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                    )
                },
                label = {
                    Text(
                        "Search",
                        modifier = Modifier
                    )
                },
                selected = currentRoute == SearchMovieDetailScreen,
                onClick = {
                    navController.navigate(SearchMovieDetailScreen) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier
                    )
                },
                label = {
                    Text(
                        "Favorite",
                        modifier = Modifier
                    )
                },
                selected = currentRoute == FavMovieDetailScreen,
                onClick = {
                    navController.navigate(FavMovieDetailScreen) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }) {
        NavHost(navController = navController, startDestination = homeRoute) {
            composable(homeRoute) {
                /*Using hiltViewModel() inside AppNavigator and passing viewModel instance to Greeting */
                Greeting(viewModel, navController)
            }
            composable("$MovieDetailScreen/{$titleId}", arguments = listOf(
                navArgument(titleId) {
                    type = NavType.StringType
                }
            )) {
                val titleId = it.arguments?.getString(titleId)
                ShowMovieDetails(titleId = titleId!!, viewModel)
            }
            composable(SearchMovieDetailScreen) {
                SearchedMovieScreen(viewModel, navController)
            }
            composable(UpcomingMoviesScreen) {
                UpcomingMoviesList(
                    viewModel,
                    context = context,
                    FavoriteScreenStateHolder(),
                    moviesViewmodel
                )
            }
            composable(FavMovieDetailScreen) {
                FavMovieScreen(
                    moviesViewmodel,
                    context,
                    FavoriteScreenStateHolder()
                )
            }
        }

    }
}
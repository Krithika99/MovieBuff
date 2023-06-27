package com.ksas.moviebuff.repository

import com.ksas.moviebuff.api.generic.*

interface MoviesMainRepo {
    //    imported from titles
    suspend fun getMoviesByTitle(page: Int): Responses

    //earlier imported from search
    suspend fun searchMovieByTitle(title: String, page: Int): SearchResponse

    //earlier imported from searchbymovieid
    suspend fun searchMovieById(id: String): MovieByIdResponse

    //earlier imported from upcomingmovies
    suspend fun upcomingMovies(page: Int): UpcomingMovies

    suspend fun upcomingMoviesWithoutPaging(): UpcomingMovies

    //earlier imported from titles
    suspend fun movieRatingsById(id: String): Ratings
}
package com.ksas.moviebuff.repository

import com.ksas.moviebuff.api.MoviesApi
import com.ksas.moviebuff.api.generic.*
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: MoviesApi) : MoviesMainRepo {

    override suspend fun getMoviesByTitle(page: Int): Responses {
        return api.getMoviesByTitle(page)
    }

    override suspend fun searchMovieByTitle(title: String, page: Int): SearchResponse {
        return api.searchMovieByTitle(title, page)
    }

    override suspend fun searchMovieById(id: String): MovieByIdResponse {
        return api.searchMovieById(id)
    }

    override suspend fun upcomingMovies(page: Int): UpcomingMovies {
        return api.upcomingMovies(page)
    }

    override suspend fun upcomingMoviesWithoutPaging(): UpcomingMovies {
        return api.upcomingMoviesWithoutPaging()
    }

    override suspend fun movieRatingsById(id: String): Ratings {
        return api.movieRatingById(id)
    }


}
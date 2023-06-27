package com.ksas.moviebuff.api

import com.ksas.moviebuff.api.generic.*
import com.ksas.moviebuff.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @Headers(
        "content-type: application/octet-stream",
        "X-RapidAPI-Key: ${Constants.apiKey}",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com"
    )
    @GET("titles")
    suspend fun getMoviesByTitle(@Query("page") page: Int): Responses

    @Headers(
        "content-type: application/octet-stream",
        "X-RapidAPI-Key: ${Constants.apiKey}",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com"
    )
    @GET("titles/search/title/{title}")
    suspend fun searchMovieByTitle(
        @Path("title") title: String,
        @Query("page") page: Int
    ): SearchResponse

    @Headers(
        "content-type: application/octet-stream",
        "X-RapidAPI-Key: ${Constants.apiKey}",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com"
    )
    @GET("/titles/{id}")
    suspend fun searchMovieById(@Path("id") id: String): MovieByIdResponse

    @Headers(
        "content-type: application/octet-stream",
        "X-RapidAPI-Key: ${Constants.apiKey}",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com"
    )
    @GET("/titles/{id}/ratings")
    suspend fun movieRatingById(@Path("id") id: String): Ratings

    @Headers(
        "content-type: application/octet-stream",
        "X-RapidAPI-Key: ${Constants.apiKey}",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com"
    )
    @GET("/titles/x/upcoming")
    suspend fun upcomingMovies(@Query("page") page: Int): UpcomingMovies

    @Headers(
        "content-type: application/octet-stream",
        "X-RapidAPI-Key: ${Constants.apiKey}",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com"
    )
    @GET("/titles/x/upcoming")
    suspend fun upcomingMoviesWithoutPaging(): UpcomingMovies


}





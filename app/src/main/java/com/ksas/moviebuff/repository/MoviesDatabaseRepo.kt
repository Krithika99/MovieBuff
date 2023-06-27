package com.ksas.moviebuff.repository

import com.ksas.moviebuff.database.Movie

interface MoviesDatabaseRepo {

    suspend fun insert(movie: Movie)

    suspend fun getAllMovies(): List<Movie>

    suspend fun delete(movie: Movie)
}
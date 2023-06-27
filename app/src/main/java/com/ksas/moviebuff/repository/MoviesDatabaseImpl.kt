package com.ksas.moviebuff.repository

import com.ksas.moviebuff.database.Movie
import com.ksas.moviebuff.database.MovieDao
import javax.inject.Inject

class MoviesDatabaseImpl @Inject constructor(private val database: MovieDao) : MoviesDatabaseRepo {
    override suspend fun insert(movie: Movie) {
        database.insertMovie(movie)
    }

    override suspend fun getAllMovies(): List<Movie> {
        return database.getMovies()
    }

    override suspend fun delete(movie: Movie) {
        database.deleteMovie(movie)
    }
}
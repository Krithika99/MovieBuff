package com.ksas.moviebuff.database

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("select * from Movie")
    suspend fun getMovies(): List<Movie>

    @Delete
    suspend fun deleteMovie(movie: Movie)

}
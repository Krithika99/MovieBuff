package com.ksas.moviebuff.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String?,
    val releaseYear: Int,
    val releaseDate: Int,
)
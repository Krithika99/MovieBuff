package com.ksas.moviebuff.api.generic

data class UpcomingMovies(
    val entries: Int,
    val next: String,
    val page: Int,
    val results: List<GenericResult>
)
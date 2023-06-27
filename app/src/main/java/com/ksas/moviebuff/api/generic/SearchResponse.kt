package com.ksas.moviebuff.api.generic

data class SearchResponse(
    val entries: Int,
    val next: String,
    val page: Int,
    val results: List<Results>
)
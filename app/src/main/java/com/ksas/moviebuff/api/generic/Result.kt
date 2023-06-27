package com.ksas.moviebuff.api.generic

data class Results(
    val id: String,
    val primaryImage: PrimaryImage?,
    val releaseDate: ReleaseDate?,
    val releaseYear: ReleaseYear?,
    val titleText: TitleText?,
    val titleType: TitleType?
)
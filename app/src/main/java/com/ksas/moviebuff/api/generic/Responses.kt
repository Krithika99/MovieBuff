package com.ksas.moviebuff.api.generic

import com.google.gson.annotations.SerializedName

data class Responses(
    @SerializedName("page") var entries: Int,
    @SerializedName("next") var next: String,
    @SerializedName("entries") var page: Int,
    @SerializedName("results") var results: List<Results>
)
package com.search.movies.data

import com.google.gson.annotations.SerializedName

data class SearchResponse  (
    @SerializedName("Search") val movies: List<MovieResponse>
){}

data class MovieResponse  (
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Country") val country: String,
    @SerializedName("imdbID") val imdbID: String
){}
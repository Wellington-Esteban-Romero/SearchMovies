package com.search.movies.services

import com.search.movies.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("?")
    suspend fun findMoviesByName(@Query("s") s:String, @Query("apikey") apikey:String):
            Response<SearchResponse>
}
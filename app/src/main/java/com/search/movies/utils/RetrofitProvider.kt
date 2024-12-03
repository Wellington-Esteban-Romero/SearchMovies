package com.search.movies.utils

import com.search.movies.services.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {

        private val BASE_URL = "https://www.omdbapi.com/?"

        fun getRetrofit(): MovieService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MovieService::class.java)
        }
    }
}
package com.search.movies.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.search.movies.R
import com.search.movies.data.MovieResponse
import com.search.movies.databinding.ActivityDetailsMovieBinding
import com.search.movies.services.MovieService
import com.search.movies.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var movieService: MovieService
    private lateinit var binding: ActivityDetailsMovieBinding

    companion object {
        val EXTRA_MOVIE_ID: String = "EXTRA_SUPERHERO_ID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init () {

        movieService =   RetrofitProvider.getRetrofit()

        val id = intent.getStringExtra(EXTRA_MOVIE_ID).orEmpty()
        getDataMovie(id)

        getActionBarSuperHero()
    }

    private fun getDataMovie (id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.findMovieByImdbID(id, MainActivity.APIKEY)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.i("Pelicula", responseBody.toString())

                    runOnUiThread {
                        createDetails(responseBody)
                    }
                }
            }
        }
    }

    private fun createDetails (movieResponse: MovieResponse) {
        Picasso.get().load(movieResponse.poster).into(binding.imgMovieDetail)
        binding.detailTitle.text = movieResponse.title
        binding.detailYear.text = movieResponse.year
        binding.detailPlot.text = movieResponse.plot
        binding.detailRunTime.text = movieResponse.runtime
        binding.detailDirector.text = movieResponse.director
        binding.detailGenre.text = movieResponse.genre
        binding.detailCountry.text = movieResponse.country
    }

    private fun getActionBarSuperHero ():Unit {
        val actionBar = supportActionBar
        actionBar!!.title = "Movies"
        actionBar!!.setDisplayShowHomeEnabled(true)
    }
}
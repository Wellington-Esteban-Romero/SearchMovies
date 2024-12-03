package com.search.movies.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.search.movies.R
import com.search.movies.adapters.MovieAdapter
import com.search.movies.data.MovieResponse
import com.search.movies.databinding.ActivityMainBinding
import com.search.movies.services.MovieService
import com.search.movies.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieService: MovieService
    private lateinit var adapterMovie: MovieAdapter

    companion object {
        val APIKEY = "bb9571fd"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init () {

        adapterMovie = MovieAdapter() { movieItem ->
            onItemSelect(movieItem)
        }
        binding.rvSearchMovies.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = adapterMovie
            hasFixedSize()
        }
        movieService =   RetrofitProvider.getRetrofit()
        getActionBarMovie()
    }

    private fun onItemSelect(movieResponse: MovieResponse) {
        val intent = Intent(this, DetailsMovieActivity::class.java)
        intent.putExtra(DetailsMovieActivity.EXTRA_MOVIE_ID, movieResponse.imdbID)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu?.findItem(R.id.actionSearch)!!

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false;
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return true
    }

    private fun searchByName (name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.findMoviesByName(name, APIKEY)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.i("Movies", responseBody.toString())
                    runOnUiThread {
                        if (responseBody.movies != null && responseBody.movies.isNotEmpty()) {
                            adapterMovie.updateMovie(responseBody.movies)
                        }
                    }
                }
            }
        }
    }

    private fun getActionBarMovie ():Unit {
        val actionBar = supportActionBar
        actionBar!!.title = "Movies"
        actionBar!!.setDisplayShowHomeEnabled(true)
    }
}
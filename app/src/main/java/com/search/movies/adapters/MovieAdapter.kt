package com.search.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.search.movies.R
import com.search.movies.data.MovieResponse
import com.search.movies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private var movies: List<MovieResponse> = emptyList(),
                   private val onClickListener: (MovieResponse) -> Unit): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], onClickListener)
    }

    fun updateMovie (list: List<MovieResponse>) {
        movies = list
        notifyDataSetChanged()
    }
}

class MovieViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    private val itemMovieBinding = ItemMovieBinding.bind(view)

    fun bind(movieResponse: MovieResponse, onClickListener: (MovieResponse) -> Unit) {
        itemMovieBinding.title.text = movieResponse.title
        itemMovieBinding.year.text = movieResponse.year
        Picasso.get().load(movieResponse.poster).into(itemMovieBinding.imgMovie);
        itemView.setOnClickListener {
            onClickListener(movieResponse)
        }
    }
}
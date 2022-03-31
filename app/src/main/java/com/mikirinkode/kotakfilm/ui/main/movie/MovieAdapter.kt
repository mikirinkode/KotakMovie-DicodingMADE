package com.mikirinkode.kotakfilm.ui.main.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.databinding.ItemsFilmBinding
import com.mikirinkode.kotakfilm.ui.detail.DetailCatalogueActivity
import com.mikirinkode.kotakfilm.utils.Constants.Companion.IMAGE_BASE_URL
import com.mikirinkode.kotakfilm.utils.MovieDiffUtil


class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var moviesList = ArrayList<MovieEntity>()

    class MovieViewHolder(private val binding: ItemsFilmBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            binding.apply {
                tvItemTitle.text = movie.title
                tvItemVote.text = movie.voteAverage.toString()
                val date: List<String> = movie.releaseDate.split("-")
                val year = date[0]
                tvItemReleaseDate.text = year
                Glide.with(itemView.context)
                    .load("${IMAGE_BASE_URL}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
                    .error(R.drawable.ic_error)
                    .into(ivPoster)
            }

            itemView.setOnClickListener{
                val moveToDetail = Intent(itemView.context, DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_ID, movie.id)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_TYPE, "MOVIE")
                itemView.context.startActivity(moveToDetail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsFilmBinding = ItemsFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsFilmBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = moviesList.size

    fun setData(newMovieList: List<MovieEntity>){
        val diffUtil = MovieDiffUtil(moviesList,newMovieList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.moviesList.clear()
        this.moviesList.addAll(newMovieList)
        diffResults.dispatchUpdatesTo(this)
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? {
        return moviesList[swipedPosition]
    }
}
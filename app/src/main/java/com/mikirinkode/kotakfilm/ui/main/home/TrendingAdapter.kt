package com.mikirinkode.kotakfilm.ui.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.databinding.TrendingItemsBinding
import com.mikirinkode.kotakfilm.ui.detail.DetailCatalogueActivity
import com.mikirinkode.kotakfilm.utils.Constants
import com.mikirinkode.kotakfilm.utils.MovieDiffUtil

class TrendingAdapter: RecyclerView.Adapter<TrendingAdapter.MovieViewHolder>() {
    private var moviesList = ArrayList<MovieEntity>()

    class MovieViewHolder(private val binding: TrendingItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            binding.apply {
                tvItemTitle.text = movie.title
                rateBar.rating = movie.voteAverage.toFloat() / 2
                Glide.with(itemView.context)
                    .load("${Constants.IMAGE_BASE_URL}${movie.backdropPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(ivPoster)
            }

            itemView.setOnClickListener{
                val moveToDetail = Intent(itemView.context, DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_MOVIE, movie)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_TYPE, "MOVIE")
                itemView.context.startActivity(moveToDetail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsBinding = TrendingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsBinding)
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
}

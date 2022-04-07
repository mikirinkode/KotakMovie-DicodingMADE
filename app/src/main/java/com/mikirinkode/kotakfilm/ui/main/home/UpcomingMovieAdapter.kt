package com.mikirinkode.kotakfilm.ui.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.databinding.UpcomingItemsBinding
import com.mikirinkode.kotakfilm.ui.detail.DetailCatalogueActivity
import com.mikirinkode.kotakfilm.utils.Constants
import com.mikirinkode.kotakfilm.utils.CatalogueDiffUtil

class UpcomingMovieAdapter: RecyclerView.Adapter<UpcomingMovieAdapter.MovieViewHolder>() {
    private var moviesList = ArrayList<CatalogueEntity>()

    class MovieViewHolder(private val binding: UpcomingItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: CatalogueEntity) {
            binding.apply {
                tvItemVote.text = movie.voteAverage.toString()
                Glide.with(itemView.context)
                    .load("${Constants.IMAGE_BASE_URL}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
                    .error(R.drawable.ic_error)
                    .into(ivPoster)
            }

            itemView.setOnClickListener{
                val moveToDetail = Intent(itemView.context, DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_FILM, movie)
                itemView.context.startActivity(moveToDetail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsBinding = UpcomingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = moviesList.size

    fun setData(newMovieList: List<CatalogueEntity>){
        val diffUtil = CatalogueDiffUtil(moviesList,newMovieList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)

        val sortedList = newMovieList.sortedByDescending { it.releaseDate }
        this.moviesList.clear()
        this.moviesList.addAll(sortedList)
        diffResults.dispatchUpdatesTo(this)
    }
}

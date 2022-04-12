package com.mikirinkode.kotakfilm.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.core.R
import com.mikirinkode.kotakfilm.core.databinding.HomeCatalogueItemsBinding
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.utils.CatalogueDiffUtil
import com.mikirinkode.kotakfilm.core.utils.Constants

class HomeCatalogueAdapter: RecyclerView.Adapter<HomeCatalogueAdapter.MovieViewHolder>() {

    private var moviesList = ArrayList<Catalogue>()
    var onItemClick: ((Catalogue) -> Unit)? = null

    inner class MovieViewHolder(private val binding: HomeCatalogueItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Catalogue) {
            binding.apply {
                tvItemVote.text = movie.voteAverage.toString()
                Glide.with(itemView.context)
                    .load("${Constants.IMAGE_BASE_URL}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
                    .error(R.drawable.ic_error)
                    .into(ivPoster)
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(moviesList[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsBinding = HomeCatalogueItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = if (moviesList.size <= 10) moviesList.size else 10

    fun setData(newMovieList: List<Catalogue>){
        val diffUtil = CatalogueDiffUtil(moviesList,newMovieList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.moviesList.clear()
        this.moviesList.addAll(newMovieList)
        diffResults.dispatchUpdatesTo(this)
    }
}

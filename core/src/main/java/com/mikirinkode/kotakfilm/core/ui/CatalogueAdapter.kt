package com.mikirinkode.kotakfilm.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.core.R
import com.mikirinkode.kotakfilm.core.databinding.CatalogueItemsBinding
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.utils.CatalogueDiffUtil
import com.mikirinkode.kotakfilm.core.utils.Constants.Companion.IMAGE_BASE_URL

class CatalogueAdapter: RecyclerView.Adapter<CatalogueAdapter.MovieViewHolder>() {

    private var moviesList = ArrayList<Catalogue>()
    var onItemClick: ((Catalogue) -> Unit)? = null

    inner class MovieViewHolder(private val binding: CatalogueItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Catalogue) {
            binding.apply {
                tvItemTitle.text = movie.title
                tvItemVote.text = movie.voteAverage.toString()
                if (movie.releaseDate == null){
                    tvItemReleaseDate.text = itemView.context.getString(R.string.no_data)
                } else {
                    val date: List<String> = movie.releaseDate.split("-")
                    val year = date[0]
                    tvItemReleaseDate.text = year
                }
                Glide.with(itemView.context)
                    .load("${IMAGE_BASE_URL}${movie.posterPath}")
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
        val itemsBinding = CatalogueItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int =  moviesList.size

    fun setData(newMovieList: List<Catalogue>){
        val diffUtil = CatalogueDiffUtil(moviesList,newMovieList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.moviesList.clear()
        this.moviesList.addAll(newMovieList)
        notifyDataSetChanged()
        diffResults.dispatchUpdatesTo(this)
    }

    fun clearList(){
        val size = moviesList.size
        moviesList.clear()
        notifyItemRangeRemoved(0, size)
    }
}
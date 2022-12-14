package com.mikirinkode.kotakmovie.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.utils.CatalogueDiffUtil
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.IMAGE_BASE_URL
import com.mikirinkode.kotakmovie.databinding.CatalogueItemsBinding

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
                onItemClick?.invoke(moviesList[bindingAdapterPosition])
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
        diffResults.dispatchUpdatesTo(this)
    }

    fun clearList(){
        val size = moviesList.size
        moviesList.clear()
        notifyItemRangeRemoved(0, size)
    }
}
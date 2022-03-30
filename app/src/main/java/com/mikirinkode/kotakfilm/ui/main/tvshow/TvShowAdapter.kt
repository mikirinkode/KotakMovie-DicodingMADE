package com.mikirinkode.kotakfilm.ui.main.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.databinding.ItemsFilmBinding
import com.mikirinkode.kotakfilm.ui.detail.DetailCatalogueActivity
import com.mikirinkode.kotakfilm.utils.Constants

class TvShowAdapter: PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    class TvShowViewHolder(private val binding: ItemsFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            binding.apply {
                tvItemTitle.text = tvShow.title
                tvItemVote.text = tvShow.voteAverage.toString()
                val date: List<String> = tvShow.releaseDate.split("-")
                val year = date[0]
                tvItemReleaseDate.text = year
                Glide.with(itemView.context)
                    .load("${Constants.IMAGE_BASE_URL}${tvShow.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
                    .error(R.drawable.ic_broken_image)
                    .centerCrop()
                    .into(ivPoster)
            }

            itemView.setOnClickListener {
                val moveToDetail = Intent(itemView.context, DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_ID, tvShow.id)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_TYPE, "TV SHOW")
                itemView.context.startActivity(moveToDetail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsFilmBinding =
            ItemsFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsFilmBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

}
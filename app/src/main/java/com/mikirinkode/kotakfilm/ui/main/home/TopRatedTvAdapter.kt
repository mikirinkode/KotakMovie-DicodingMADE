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
import com.mikirinkode.kotakfilm.utils.CatalogueDiffUtil
import com.mikirinkode.kotakfilm.utils.Constants

class TopRatedTvAdapter: RecyclerView.Adapter<TopRatedTvAdapter.TvShowViewHolder>() {

    private var tvShowsList = ArrayList<CatalogueEntity>()

    class TvShowViewHolder(private val binding: UpcomingItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: CatalogueEntity) {
            binding.apply {
                tvItemVote.text = tvShow.voteAverage.toString()
                Glide.with(itemView.context)
                    .load("${Constants.IMAGE_BASE_URL}${tvShow.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
                    .error(R.drawable.ic_error)
                    .into(ivPoster)
            }

            itemView.setOnClickListener{
                val moveToDetail = Intent(itemView.context, DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_FILM, tvShow)
                itemView.context.startActivity(moveToDetail)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsBinding = UpcomingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = tvShowsList[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = if (tvShowsList.size <= 10) tvShowsList.size else 10

    fun setData(newTvShowList: List<CatalogueEntity>){
        val diffUtil = CatalogueDiffUtil(tvShowsList, newTvShowList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.tvShowsList.clear()
        this.tvShowsList.addAll(newTvShowList)
        diffResults.dispatchUpdatesTo(this)
    }

}

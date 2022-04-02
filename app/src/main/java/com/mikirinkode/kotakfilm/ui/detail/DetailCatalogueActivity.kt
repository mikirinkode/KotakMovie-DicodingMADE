package com.mikirinkode.kotakfilm.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.databinding.ActivityDetailCatalogueBinding
import com.mikirinkode.kotakfilm.ui.main.movie.MovieViewModel
import com.mikirinkode.kotakfilm.ui.main.tvshow.TvShowViewModel
import com.mikirinkode.kotakfilm.utils.Constants.Companion.IMAGE_BASE_URL
import com.mikirinkode.kotakfilm.vo.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailCatalogueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCatalogueBinding
    private lateinit var movieTitle: String
    private val movieViewModel: MovieViewModel by viewModels()
    private val tvShowViewModel: TvShowViewModel by viewModels()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCatalogueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extra = intent.extras
        val type = extra?.getString(EXTRA_TYPE).toString()
        val movie = intent.getParcelableExtra<MovieEntity>(EXTRA_MOVIE)
        val tvShow = intent.getParcelableExtra<TvShowEntity>(EXTRA_TV_SHOW)
        if (type == "MOVIE") getDetailMovie(movie, type) else getDetailTvShow(tvShow, type)

        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnTryAgain.setOnClickListener {
                if (type == "MOVIE") getDetailMovie(movie, type) else getDetailTvShow(tvShow, type)
            }

            toggleAddItem.setOnClickListener {
                isFavorite = !isFavorite
                toggleAddItem.isChecked = isFavorite
                if (isFavorite) Toast.makeText(
                    this@DetailCatalogueActivity,
                    getString(R.string.added_to_playlist),
                    Toast.LENGTH_SHORT
                ).show()
                else Toast.makeText(
                    this@DetailCatalogueActivity,
                    getString(R.string.removed_from_playlist),
                    Toast.LENGTH_SHORT
                ).show()

                if (type == "MOVIE") {
                    movieViewModel.setMoviePlaylist()
                } else if (type == "TV SHOW") {
                    tvShowViewModel.setTvShowPlaylist()
                }
            }
        }
    }


    private fun getDetailMovie(movie: MovieEntity?, type: String) {
        binding.apply {
            movie?.let { it ->
                isFavorite = it.isOnPlaylist
                toggleAddItem.isChecked = isFavorite
                movieTitle = it.title
                setData(
                    it.title,
                    it.overview,
                    it.genres,
                    it.releaseDate,
                    it.tagline,
                    it.voteAverage,
                    it.runtime,
                    it.posterPath,
                    it.backdropPath,
                    type,
                )
            }
            icLoading.visibility = View.VISIBLE
            onFailMsg.visibility = View.GONE
            tvLabelRelease.visibility = View.GONE
            movie?.let { movieViewModel.setSelectedMovie(it) }
            movieViewModel.movieDetail.observe(this@DetailCatalogueActivity) { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> {
                            icLoading.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            icLoading.visibility = View.GONE
                            movie.data?.let {
                                setData(
                                    it.title,
                                    it.overview,
                                    it.genres,
                                    it.releaseDate,
                                    it.tagline,
                                    it.voteAverage,
                                    it.runtime,
                                    it.posterPath,
                                    it.backdropPath,
                                    type
                                )
                            }
                        }
                        Status.ERROR -> {
                            icLoading.visibility = View.GONE
                            onFailMsg.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }


    private fun getDetailTvShow(tvShow: TvShowEntity?, type: String) {
        binding.apply {
            tvShow?.let { it ->
                isFavorite = it.isOnPlaylist
                toggleAddItem.isChecked = isFavorite
                movieTitle = it.title
                setData(
                    it.title,
                    it.overview,
                    it.genres,
                    it.releaseDate,
                    it.tagline,
                    it.voteAverage,
                    it.runtime,
                    it.posterPath,
                    it.backdropPath,
                    type,
                )
            }
            icLoading.visibility = View.VISIBLE
            onFailMsg.visibility = View.GONE
            tvLabelRelease.visibility = View.GONE
            tvShow?.let { tvShowViewModel.setSelectedTvShow(it) }
            tvShowViewModel.tvShowDetail.observe(this@DetailCatalogueActivity) { tvShow ->
                if (tvShow != null) {
                    when (tvShow.status) {
                        Status.LOADING -> {
                            icLoading.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            icLoading.visibility = View.GONE
                            tvShow.data?.let {
                                setData(
                                    it.title,
                                    it.overview,
                                    it.genres,
                                    it.releaseDate,
                                    it.tagline,
                                    it.voteAverage,
                                    it.runtime,
                                    it.posterPath.toString(),
                                    it.backdropPath.toString(),
                                    type
                                )
                            }
                        }
                        Status.ERROR -> {
                            icLoading.visibility = View.GONE
                            onFailMsg.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setData(
        title: String,
        overview: String?,
        genres: String?,
        releaseDate: String?,
        tagline: String?,
        voteAverage: Double,
        runtime: Int?,
        posterPath: String?,
        backdropPath: String?,
        category: String
    ) {
        binding.apply {
            tvDetailTitle.text = title
            if (overview == "" || overview == null) getString(R.string.no_data) else tvDetailDescription.text =
                overview
            tvDetailRelease.text = releaseDate ?: "-No Data-"
            tvDetailQuote.text = tagline ?: ""
            tvDetailRating.text = voteAverage.toString()
            tvDetailCategory.text = getString(R.string.category, category)
            if (runtime != null) {
                if (category == "MOVIE") {
                    val hours = runtime.div(60)
                    val minutes = runtime.rem(60)
                    tvDetailDuration.text = getString(R.string.runtime, hours, minutes)
                } else {
                    tvDetailDuration.text = getString(R.string.episodeRuntime, runtime)
                }
            } else {
                tvDetailDuration.text = getString(R.string.no_data)
            }

            tvLabelRelease.visibility = View.VISIBLE

            tvDetailGenre.text = genres ?: getString(R.string.no_data)

            ivDetailPoster.loadImage("${IMAGE_BASE_URL}${posterPath}")
            ivDetailPosterBg.loadImage("${IMAGE_BASE_URL}${backdropPath}")
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
            .error(R.drawable.ic_error)
            .into(this)
    }


    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }
}
package com.mikirinkode.kotakfilm.ui.detail

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.databinding.ActivityDetailCatalogueBinding
import com.mikirinkode.kotakfilm.databinding.YoutubePlayerPopupBinding
import com.mikirinkode.kotakfilm.ui.main.movie.MovieViewModel
import com.mikirinkode.kotakfilm.ui.main.tvshow.TvShowViewModel
import com.mikirinkode.kotakfilm.utils.Constants.Companion.IMAGE_BASE_URL
import com.mikirinkode.kotakfilm.vo.Status
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailCatalogueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCatalogueBinding
    private lateinit var popupBinding: YoutubePlayerPopupBinding
    private lateinit var movieTitle: String
    private val movieViewModel: MovieViewModel by viewModels()
    private val tvShowViewModel: TvShowViewModel by viewModels()
    private var isFavorite: Boolean = false
    private var trailerVideoKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCatalogueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extra = intent.extras
        val type = extra?.getString(EXTRA_TYPE).toString()
        val movie = intent.getParcelableExtra<MovieEntity>(EXTRA_MOVIE)
        val tvShow = intent.getParcelableExtra<TvShowEntity>(EXTRA_TV_SHOW)
        if (type == "MOVIE") {
            getDetailMovie(movie, type)
            observeMovieTrailer(movie)
        } else {
            getDetailTvShow(tvShow, type)
            observeTvTrailer(tvShow)
        }

        with(binding) {
            if (isFavorite) {
                btnRemoveFromPlaylist.visibility = View.VISIBLE
                btnAddToPlaylist.visibility = View.GONE
            } else {
                btnRemoveFromPlaylist.visibility = View.GONE
                btnAddToPlaylist.visibility = View.VISIBLE
            }

            btnBack.setOnClickListener { onBackPressed() }

            btnPlayTrailer.setOnClickListener { showYouTubePlayer() }

            btnAddToPlaylist.setOnClickListener { btnPlaylistOnClick(type) }

            btnRemoveFromPlaylist.setOnClickListener { btnPlaylistOnClick(type) }

            btnShare.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Watch $movieTitle on KotakFilm")
                shareIntent.type = "text/plain"
                startActivity(Intent.createChooser(shareIntent, "Share To:"))
            }

            btnTryAgain.setOnClickListener {
                if (type == "MOVIE") {
                    getDetailMovie(movie, type)
                    observeMovieTrailer(movie)
                } else {
                    getDetailTvShow(tvShow, type)
                    observeTvTrailer(tvShow)
                }
            }
        }
    }

    private fun showYouTubePlayer() {
        if(trailerVideoKey != ""){
            popupBinding = YoutubePlayerPopupBinding.inflate(layoutInflater)
            popupBinding.apply {
                lifecycle.addObserver(youTubePlayerView)
                val listener: YouTubePlayerListener =
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.loadOrCueVideo(lifecycle, trailerVideoKey, 0f)
                        }
                    }
                youTubePlayerView.initialize(listener)

                val dialog = Dialog(this@DetailCatalogueActivity)
                dialog.setContentView(popupBinding.root)
                dialog.window?.setBackgroundDrawable(
                    (AppCompatResources.getDrawable(
                        applicationContext,
                        R.color.dark_500
                    ))
                )
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(dialog.window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT

                dialog.window?.attributes = lp
                dialog.show()
            }
        } else {
            Toast.makeText(
                this@DetailCatalogueActivity,
                getString(R.string.no_data),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            popupBinding.youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            popupBinding.youTubePlayerView.exitFullScreen()
        }
    }


    private fun btnPlaylistOnClick(type: String) {
        binding.apply {
            isFavorite = !isFavorite
            if (isFavorite) {
                Toast.makeText(
                    this@DetailCatalogueActivity,
                    getString(R.string.added_to_playlist),
                    Toast.LENGTH_SHORT
                ).show()
                btnRemoveFromPlaylist.visibility = View.VISIBLE
                btnAddToPlaylist.visibility = View.GONE
            } else {
                Toast.makeText(
                    this@DetailCatalogueActivity,
                    getString(R.string.removed_from_playlist),
                    Toast.LENGTH_SHORT
                ).show()
                btnRemoveFromPlaylist.visibility = View.GONE
                btnAddToPlaylist.visibility = View.VISIBLE
            }

            if (type == "MOVIE") {
                movieViewModel.setMoviePlaylist()
            } else if (type == "TV SHOW") {
                tvShowViewModel.setTvShowPlaylist()
            }
        }
    }

    private fun getDetailMovie(movie: MovieEntity?, type: String) {
        binding.apply {
            movie?.let { it ->
                isFavorite = it.isOnPlaylist
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

    private fun observeMovieTrailer(movie: MovieEntity?) {
        binding.apply {
            icLoading.visibility = View.VISIBLE
            if (movie != null) {
                movieViewModel.getMovieTrailer(movie)
                    .observe(this@DetailCatalogueActivity) { trailer ->
                        if (trailer != null) {
                            when (trailer.status) {
                                Status.LOADING -> {
                                    icLoading.visibility = View.VISIBLE
                                }
                                Status.SUCCESS -> {
                                    icLoading.visibility = View.GONE
                                    if (trailer.data != null && trailer.data.isNotEmpty()) {
                                        trailerVideoKey = trailer.data[0].key
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
    }

    private fun observeTvTrailer(tvShow: TvShowEntity?) {
        binding.apply {
            icLoading.visibility = View.VISIBLE
            if (tvShow != null) {
                tvShowViewModel.getTvTrailer(tvShow)
                    .observe(this@DetailCatalogueActivity) { trailer ->
                        if (trailer != null) {
                            when (trailer.status) {
                                Status.LOADING -> {
                                    icLoading.visibility = View.VISIBLE
                                }
                                Status.SUCCESS -> {
                                    icLoading.visibility = View.GONE
                                    if (trailer.data != null && trailer.data.isNotEmpty()) {
                                        trailerVideoKey = trailer.data[0].key
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
    }

    private fun getDetailTvShow(tvShow: TvShowEntity?, type: String) {
        binding.apply {
            tvShow?.let { it ->
                isFavorite = it.isOnPlaylist
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
            tvDetailDescription.text =
                if (overview == "" || overview == null) getString(R.string.no_data) else overview
            if (releaseDate == null || releaseDate == "") {
                tvDetailRelease.text = getString(R.string.no_data)
            } else {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val date = dateFormat.parse(releaseDate)
                if (date != null) {
                    val dateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date)
                    tvDetailRelease.text = dateFormatted
                } else {
                    tvDetailRelease.text = getString(R.string.no_data)
                }
            }

            tvDetailQuote.text = if (tagline != null) getString(
                R.string.quote,
                tagline
            ) else getString(R.string.quote, "")
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

            tvDetailGenre.text =
                if (genres == null || genres == "") getString(R.string.no_genre_data) else genres

            ivDetailPoster.loadImage("${IMAGE_BASE_URL}${posterPath}")
            ivDetailPosterBackdrop.loadImage("${IMAGE_BASE_URL}${backdropPath}")
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
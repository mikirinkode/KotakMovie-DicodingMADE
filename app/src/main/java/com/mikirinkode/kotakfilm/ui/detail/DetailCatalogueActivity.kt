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
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.utils.Constants.Companion.IMAGE_BASE_URL
import com.mikirinkode.kotakfilm.core.vo.Resource
import com.mikirinkode.kotakfilm.core.vo.Status
import com.mikirinkode.kotakfilm.databinding.ActivityDetailCatalogueBinding
import com.mikirinkode.kotakfilm.databinding.YoutubePlayerPopupBinding
import com.mikirinkode.kotakfilm.ui.main.movie.MovieViewModel
import com.mikirinkode.kotakfilm.ui.main.tvshow.TvShowViewModel
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

        val catalogue = intent.getParcelableExtra<Catalogue>(EXTRA_FILM)

        if (catalogue != null){
            if (catalogue.isTvShow) {
                getDetailTvShow(catalogue)
                observeTvTrailer(catalogue)
            } else {
                getDetailMovie(catalogue)
                observeMovieTrailer(catalogue)
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

                btnAddToPlaylist.setOnClickListener { btnPlaylistOnClick(catalogue) }

                btnRemoveFromPlaylist.setOnClickListener { btnPlaylistOnClick(catalogue) }

                btnShare.setOnClickListener {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Watch $movieTitle on KotakFilm")
                    shareIntent.type = "text/plain"
                    startActivity(Intent.createChooser(shareIntent, "Share To:"))
                }

                btnTryAgain.setOnClickListener {
                    if (catalogue.isTvShow) {
                        getDetailTvShow(catalogue)
                        observeTvTrailer(catalogue)
                    } else {
                        getDetailMovie(catalogue)
                        observeMovieTrailer(catalogue)
                    }
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


    private fun btnPlaylistOnClick(catalogue: Catalogue) {
        binding.apply {
            isFavorite = !isFavorite
            if (isFavorite) {
                if (catalogue.isTvShow)
                    tvShowViewModel.insertTvShowToPlaylist() else movieViewModel.insertMovieToPlaylist()
                Toast.makeText(
                    this@DetailCatalogueActivity,
                    getString(R.string.added_to_playlist),
                    Toast.LENGTH_SHORT
                ).show()
                btnRemoveFromPlaylist.visibility = View.VISIBLE
                btnAddToPlaylist.visibility = View.GONE
            } else {
                if (catalogue.isTvShow)
                    tvShowViewModel.removeTvShowFromPlaylist(catalogue) else movieViewModel.removeMovieFromPlaylist(catalogue)
                Toast.makeText(
                    this@DetailCatalogueActivity,
                    getString(R.string.removed_from_playlist),
                    Toast.LENGTH_SHORT
                ).show()
                btnRemoveFromPlaylist.visibility = View.GONE
                btnAddToPlaylist.visibility = View.VISIBLE
            }


        }
    }

    private fun getDetailMovie(movie: Catalogue?) {
        binding.apply {
            movie?.let { it ->
                isFavorite = it.isOnPlaylist
                movieTitle = it.title
                setData(it)
            }
            icLoading.visibility = View.VISIBLE
            onFailMsg.visibility = View.GONE
            movie?.let { movieViewModel.setSelectedMovie(it) }
            movieViewModel.movieDetail.observe(this@DetailCatalogueActivity) { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> {
                            icLoading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            icLoading.visibility = View.GONE
                            movie.data?.let {
                                setData(it)
                            }
                        }
                        is Resource.Error -> {
                            icLoading.visibility = View.GONE
                            onFailMsg.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun observeMovieTrailer(movie: Catalogue?) {
        binding.apply {
            icLoading.visibility = View.VISIBLE
            if (movie != null) {
                movieViewModel.getMovieTrailer(movie)
                    .observe(this@DetailCatalogueActivity) { trailer ->
                        if (trailer != null) {
                            when (trailer) {
                                is Resource.Loading -> {
                                    icLoading.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    icLoading.visibility = View.GONE
                                    if (trailer.data != null) {
                                        trailerVideoKey = trailer.data.key
                                    }
                                }
                                is Resource.Error -> {
                                    icLoading.visibility = View.GONE
                                    onFailMsg.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun observeTvTrailer(tvShow: Catalogue?) {
        binding.apply {
            icLoading.visibility = View.VISIBLE
            if (tvShow != null) {
                tvShowViewModel.getTvTrailer(tvShow)
                    .observe(this@DetailCatalogueActivity) { trailer ->
                        if (trailer != null) {
                            when (trailer) {
                                is Resource.Loading -> {
                                    icLoading.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    icLoading.visibility = View.GONE
                                    if (trailer.data != null) {
                                        trailerVideoKey = trailer.data.key
                                    }
                                }
                                is Resource.Error -> {
                                    icLoading.visibility = View.GONE
                                    onFailMsg.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun getDetailTvShow(tvShow: Catalogue?) {
        binding.apply {
            tvShow?.let { it ->
                isFavorite = it.isOnPlaylist
                movieTitle = it.title
                setData(it)
            }
            icLoading.visibility = View.VISIBLE
            onFailMsg.visibility = View.GONE
            tvShow?.let { tvShowViewModel.setSelectedTvShow(it) }
            tvShowViewModel.tvShowDetail.observe(this@DetailCatalogueActivity) { tvShow ->
                if (tvShow != null) {
                    when (tvShow) {
                        is Resource.Loading -> {
                            icLoading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            icLoading.visibility = View.GONE
                            tvShow.data?.let {
                                setData(it)
                            }
                        }
                        is Resource.Error -> {
                            icLoading.visibility = View.GONE
                            onFailMsg.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setData(catalogue: Catalogue) {
        binding.apply {
            catalogue.apply {
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

                val category = if (isTvShow) "TV SHOW" else "MOVIE"

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
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh))
            .error(R.drawable.ic_error)
            .into(this)
    }


    companion object {
        const val EXTRA_FILM = "extra_film"
    }
}
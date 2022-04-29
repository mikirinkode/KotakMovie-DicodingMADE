package com.mikirinkode.kotakmovie.core.domain.usecase

import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.repository.IMovieRepository
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {

    override fun searchMovies(query: String) = movieRepository.searchMovies(query)

    override fun getPopularMovies(sort: String, shouldFetchAgain: Boolean) =
        movieRepository.getPopularMovies(sort, shouldFetchAgain)

    override fun getUpcomingMovies() = movieRepository.getUpcomingMovies()

    override fun getTrendingThisWeekList() = movieRepository.getTrendingThisWeekList()

    override fun getMovieDetail(movie: Catalogue) = movieRepository.getMovieDetail(movie)

    override fun getMoviePlaylist() = movieRepository.getMoviePlaylist()

    override fun getMovieTrailer(movie: Catalogue) = movieRepository.getMovieTrailer(movie)

    override suspend fun insertPlaylistItem(item: Catalogue, state: Boolean) =
        movieRepository.insertPlaylistItem(item, state)

    override suspend fun removePlaylistItem(item: Catalogue) =
        movieRepository.removePlaylistItem(item)

    override fun getTvTrailer(tvShow: Catalogue) = movieRepository.getTvTrailer(tvShow)

    override fun getPopularTvShows(sort: String, shouldFetchAgain: Boolean) =
        movieRepository.getPopularTvShows(sort, shouldFetchAgain)

    override fun getTopTvShowList() = movieRepository.getTopTvShowList()

    override fun getTvShowDetail(tvShow: Catalogue) = movieRepository.getTvShowDetail(tvShow)

    override fun getTvShowPlaylist() = movieRepository.getTvShowPlaylist()
}
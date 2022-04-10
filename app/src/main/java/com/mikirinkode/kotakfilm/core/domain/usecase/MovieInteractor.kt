package com.mikirinkode.kotakfilm.core.domain.usecase

import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun searchMovies(query: String) = movieRepository.searchMovies(query)

    override fun getPopularMovies(sort: String) = movieRepository.getPopularMovies(sort)

    override fun getUpcomingMovies() = movieRepository.getUpcomingMovies()

    override fun getTrendingMovies() = movieRepository.getTrendingMovies()

    override fun getMovieDetail(movie: Catalogue) = movieRepository.getMovieDetail(movie)

    override fun getMoviePlaylist() = movieRepository.getMoviePlaylist()

    override fun setMoviePlaylist(movie: Catalogue, state: Boolean) = movieRepository.setMoviePlaylist(movie, state)

    override fun getMovieTrailer(movie: Catalogue) = movieRepository.getMovieTrailer(movie)


    override fun getTvTrailer(tvShow: Catalogue) = movieRepository.getTvTrailer(tvShow)

    override fun getPopularTvShows(sort: String) = movieRepository.getPopularTvShows(sort)

    override fun getTopTvShowList() = movieRepository.getTopTvShowList()

    override fun getTvShowDetail(tvShow: Catalogue) = movieRepository.getTvShowDetail(tvShow)

    override fun getTvShowPlaylist() = movieRepository.getTvShowPlaylist()

    override fun setTvShowPlaylist(tvShow: Catalogue, state: Boolean) = movieRepository.setTvShowPlaylist(tvShow, state)
}
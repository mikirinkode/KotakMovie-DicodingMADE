package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.data.source.local.LocalDataSource
import com.mikirinkode.kotakfilm.data.source.remote.ApiResponse
import com.mikirinkode.kotakfilm.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakfilm.data.source.remote.response.*
import com.mikirinkode.kotakfilm.utils.AppExecutors
import com.mikirinkode.kotakfilm.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    override fun searchMovies(query: String): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.searchMovies(query)
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data == null || data.isEmpty() || data.size < 5
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.searchMovies(query)
            }

            override fun saveCallResult(movieResponses: MovieListResponse) {
                if (movieResponses != null) {
                    movieResponses.results.forEach {
                        val movie = MovieEntity(
                            it.id, it.title, it.releaseDate, it.overview, null,
                            null, null, it.voteAverage, it.posterPath, it.backdropPath
                        )
                        localDataSource.insertSearchResult(movie)
                    }
                }
            }
        }.asLiveData()
    }


    override fun getPopularMovies(sort: String): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getMovieList(sort)
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getPopularMovieList()
            }

            override fun saveCallResult(movieResponses: MovieListResponse) {
                val movieList = ArrayList<MovieEntity>()
                if (movieResponses != null) {
                    movieResponses.results.forEach {
                        val movie = MovieEntity(
                            it.id, it.title, it.releaseDate, it.overview, null,
                            null, null, it.voteAverage, it.posterPath, it.backdropPath
                        )
                        movieList.add(movie)
                    }
                }
                localDataSource.insertMovieList(movieList)
            }
        }.asLiveData()
    }

    override fun getTrendingMovies(): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getTrendingMovies()
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getTrendingMovieList()
            }

            override fun saveCallResult(movieResponses: MovieListResponse) {
                val movieList = ArrayList<MovieEntity>()
                if (movieResponses != null) {
                    movieResponses.results.forEach {
                        val movie = MovieEntity(
                            it.id,
                            it.title,
                            it.releaseDate,
                            it.overview,
                            null,
                            null,
                            null,
                            it.voteAverage,
                            it.posterPath,
                            it.backdropPath,
                            isOnTrending = true
                        )
                        movieList.add(movie)
                    }
                }
                localDataSource.insertMovieList(movieList)
            }
        }.asLiveData()
    }


    override fun getUpcomingMovies(): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getUpcomingMovies()
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getUpcomingMovieList()
            }

            override fun saveCallResult(movieResponses: MovieListResponse) {
                val movieList = ArrayList<MovieEntity>()
                if (movieResponses != null) {
                    movieResponses.results.forEach {
                        val movie = MovieEntity(
                            it.id,
                            it.title,
                            it.releaseDate,
                            it.overview,
                            null,
                            null,
                            null,
                            it.voteAverage,
                            it.posterPath,
                            it.backdropPath,
                            isUpcoming = true
                        )
                        movieList.add(movie)
                    }
                }
                localDataSource.insertMovieList(movieList)
            }
        }.asLiveData()
    }


    override fun getMovieDetail(movie: MovieEntity): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovieDetail(movie.id)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data?.genres == null && data?.runtime == null && data?.tagline == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(movie.id)
            }

            override fun saveCallResult(movieResponse: MovieDetailResponse) {
                if (movieResponse != null) {
                    movieResponse.apply {
                        val genreList = ArrayList<String>()
                        genres.forEach { genreList.add(it.name) }
                        val genre = genreList.joinToString(separator = ", ")

                        val movieDetail = MovieEntity(
                            id,
                            title,
                            releaseDate,
                            overview,
                            tagline,
                            genre,
                            runtime,
                            voteAverage,
                            posterPath,
                            backdropPath,
                            isUpcoming = movie.isUpcoming,
                            isOnTrending = movie.isOnTrending,
                        )
                        localDataSource.updateMovieData(movieDetail)
                    }
                }
            }
        }.asLiveData()
    }


    override fun getMovieTrailer(movie: MovieEntity): LiveData<Resource<List<TrailerVideoEntity>>> {
        return object :
            NetworkBoundResource<List<TrailerVideoEntity>, TrailerVideoResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TrailerVideoEntity>> {
                return localDataSource.getMovieTrailer(movie.id)
            }

            override fun shouldFetch(data: List<TrailerVideoEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getMovieTrailer(movie.id)
            }

            override fun saveCallResult(trailerVideoResponse: TrailerVideoResponse) {
                if (trailerVideoResponse != null) {
                    trailerVideoResponse.results.forEach {
                        if (it.official && it.site == "YouTube" && it.type == "Trailer") {
                            localDataSource.insertMovieTrailer(
                                TrailerVideoEntity(
                                    it.id,
                                    movie.id,
                                    it.key,
                                    it.name,
                                    it.site,
                                    it.type,
                                    it.official
                                )
                            )
                        }
                    }
                }
            }
        }.asLiveData()
    }

    override fun getMoviePlaylist(): LiveData<List<MovieEntity>> {
        return localDataSource.getMoviePlaylist()
    }

    override fun setMoviePlaylist(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setMoviePlaylist(movie, state) }
    }

    override fun getPopularTvShows(sort: String): LiveData<Resource<List<TvShowEntity>>> {
        return object : NetworkBoundResource<List<TvShowEntity>, TvShowListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowEntity>> {
                return localDataSource.getTvShowList(sort)
            }

            override fun shouldFetch(data: List<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getPopularTvShowList()
            }

            override fun saveCallResult(tvShowResponses: TvShowListResponse) {
                val tvShowList = ArrayList<TvShowEntity>()
                if (tvShowResponses != null) {
                    tvShowResponses.results.forEach {
                        val tvShow = TvShowEntity(
                            it.id, it.name, it.firstAirDate, it.overview, null,
                            null, null, it.voteAverage, it.posterPath, it.backdropPath
                        )
                        tvShowList.add(tvShow)
                    }
                }
                localDataSource.insertTvShowList(tvShowList)
            }
        }.asLiveData()
    }

    override fun getAiringTodayTvShows(): LiveData<Resource<List<TvShowEntity>>> {
        return object : NetworkBoundResource<List<TvShowEntity>, TvShowListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowEntity>> {
                return localDataSource.getAiringTodayTvShows()
            }

            override fun shouldFetch(data: List<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getAiringTodayTvShowList()
            }

            override fun saveCallResult(tvShowResponses: TvShowListResponse) {
                val tvShowList = ArrayList<TvShowEntity>()
                if (tvShowResponses != null) {
                    tvShowResponses.results.forEach {
                        val tvShow = TvShowEntity(
                            it.id,
                            it.name,
                            it.firstAirDate,
                            it.overview,
                            null,
                            null,
                            null,
                            it.voteAverage,
                            it.posterPath,
                            it.backdropPath,
                            isAiringToday = true
                        )
                        tvShowList.add(tvShow)
                    }
                }
                localDataSource.insertTvShowList(tvShowList)
            }
        }.asLiveData()
    }


    override fun getTvShowDetail(tvShow: TvShowEntity): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return localDataSource.getTvShowDetail(tvShow.id)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean {
                return data?.genres == null && data?.runtime == null && data?.tagline == null
            }

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(tvShow.id)
            }

            override fun saveCallResult(tvShowResponse: TvShowDetailResponse) {
                if (tvShowResponse != null) {
                    tvShowResponse.apply {
                        val genreList = ArrayList<String>()
                        genres.forEach { genreList.add(it.name) }
                        val genre = genreList.joinToString(separator = ", ")

                        val tvShowDetail = TvShowEntity(
                            id,
                            name,
                            firstAirDate,
                            overview,
                            tagline,
                            genre,
                            if (episodeRunTime.isEmpty()) 0 else episodeRunTime[0],
                            voteAverage,
                            posterPath,
                            backdropPath,
                            isAiringToday = tvShow.isAiringToday
                        )
                        localDataSource.updateTvShowData(tvShowDetail)
                    }
                }
            }
        }.asLiveData()
    }

    override fun getTvShowPlaylist(): LiveData<List<TvShowEntity>> {
        return localDataSource.getTvShowPlaylist()
    }

    override fun setTvShowPlaylist(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setTvShowPlaylist(tvShow, state) }
    }


    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}
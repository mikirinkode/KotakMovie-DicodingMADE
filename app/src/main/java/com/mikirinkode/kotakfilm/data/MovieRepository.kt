package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
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

    override fun searchMovies(query: String): LiveData<Resource<List<CatalogueEntity>>> {
        val movieListResult = MutableLiveData<List<CatalogueEntity>>()
        return object: NetworkOnlyResource<List<CatalogueEntity>, MovieListResponse>(appExecutors){
            override fun loadFromNetwork(data: MovieListResponse): LiveData<List<CatalogueEntity>> {
                val movieList = ArrayList<CatalogueEntity>()
                data.results.forEach {
                    val movie = CatalogueEntity(
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
                    )
                    movieList.add(movie)
                }
                movieListResult.postValue(movieList)
                return movieListResult
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.searchMovies(query)
            }

        }.asLiveData()
    }


    override fun getPopularMovies(sort: String): LiveData<Resource<List<CatalogueEntity>>> {
        return object :
            NetworkBoundResource<List<CatalogueEntity>, MovieListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CatalogueEntity>> {
                return localDataSource.getMovieList(sort)
            }

            override fun shouldFetch(data: List<CatalogueEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getPopularMovieList()
            }

            override fun saveCallResult(movieResponses: MovieListResponse) {
                val movieList = ArrayList<CatalogueEntity>()
                if (movieResponses != null) {
                    movieResponses.results.forEach {
                        val movie = CatalogueEntity(
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

    override fun getTrendingMovies(): LiveData<Resource<List<CatalogueEntity>>> {
        val movieListResult = MutableLiveData<List<CatalogueEntity>>()
        return object: NetworkOnlyResource<List<CatalogueEntity>, MovieListResponse>(appExecutors){
            override fun loadFromNetwork(data: MovieListResponse): LiveData<List<CatalogueEntity>> {
                val movieList = ArrayList<CatalogueEntity>()
                data.results.forEach {
                    val movie = CatalogueEntity(
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
                        isOnTrending = true,
                    )
                    movieList.add(movie)
                }
                movieListResult.postValue(movieList)
                return movieListResult
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getTrendingMovieList()
            }

        }.asLiveData()
    }


    override fun getUpcomingMovies(): LiveData<Resource<List<CatalogueEntity>>> {
        val movieListResult = MutableLiveData<List<CatalogueEntity>>()
        return object: NetworkOnlyResource<List<CatalogueEntity>, MovieListResponse>(appExecutors){
            override fun loadFromNetwork(data: MovieListResponse): LiveData<List<CatalogueEntity>> {
                val movieList = ArrayList<CatalogueEntity>()
                data.results.forEach {
                    val movie = CatalogueEntity(
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
                        isUpcoming = true,
                    )
                    movieList.add(movie)
                }
                movieListResult.postValue(movieList)
                return movieListResult
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getUpcomingMovieList()
            }

        }.asLiveData()
    }


    override fun getMovieDetail(movie: CatalogueEntity): LiveData<Resource<CatalogueEntity>> {
        return object : NetworkBoundResource<CatalogueEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<CatalogueEntity> {
                return localDataSource.getMovieDetail(movie.id)
            }

            override fun shouldFetch(data: CatalogueEntity?): Boolean {
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

                        val movieDetail = CatalogueEntity(
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


    override fun getMovieTrailer(movie: CatalogueEntity): LiveData<Resource<List<TrailerVideoEntity>>> {
        return object :
            NetworkBoundResource<List<TrailerVideoEntity>, TrailerVideoResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TrailerVideoEntity>> {
                return localDataSource.getVideoTrailer(movie.id)
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
                        if (it.site == "YouTube" && it.type == "Trailer") {
                            localDataSource.insertVideoTrailer(
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

    override fun getTvTrailer(tvShow: CatalogueEntity): LiveData<Resource<List<TrailerVideoEntity>>> {
        return object :
            NetworkBoundResource<List<TrailerVideoEntity>, TrailerVideoResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TrailerVideoEntity>> {
                return localDataSource.getVideoTrailer(tvShow.id)
            }

            override fun shouldFetch(data: List<TrailerVideoEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getTvTrailer(tvShow.id)
            }

            override fun saveCallResult(trailerVideoResponse: TrailerVideoResponse) {
                if (trailerVideoResponse != null) {
                    trailerVideoResponse.results.forEach {
                        if (it.official && it.site == "YouTube" && it.type == "Trailer") {
                            localDataSource.insertVideoTrailer(
                                TrailerVideoEntity(
                                    it.id,
                                    tvShow.id,
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


    override fun getMoviePlaylist(): LiveData<List<CatalogueEntity>> {
        return localDataSource.getMoviePlaylist()
    }

    override fun setMoviePlaylist(movie: CatalogueEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setMoviePlaylist(movie, state) }
    }

    override fun getPopularTvShows(sort: String): LiveData<Resource<List<CatalogueEntity>>> {
        return object :
            NetworkBoundResource<List<CatalogueEntity>, TvShowListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CatalogueEntity>> {
                return localDataSource.getTvShowList(sort)
            }

            override fun shouldFetch(data: List<CatalogueEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getPopularTvShowList()
            }

            override fun saveCallResult(tvShowResponses: TvShowListResponse) {
                val tvShowList = ArrayList<CatalogueEntity>()
                if (tvShowResponses != null) {
                    tvShowResponses.results.forEach {
                        val tvShow = CatalogueEntity(
                            it.id, it.name, it.firstAirDate, it.overview, null,
                            null, null, it.voteAverage, it.posterPath, it.backdropPath,
                            isTvShow = true
                        )
                        tvShowList.add(tvShow)
                    }
                }
                localDataSource.insertTvShowList(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTopTvShowList(): LiveData<Resource<List<CatalogueEntity>>> {
        val tvShowResult = MutableLiveData<List<CatalogueEntity>>()
        return object: NetworkOnlyResource<List<CatalogueEntity>, TvShowListResponse>(appExecutors){
            override fun loadFromNetwork(data: TvShowListResponse): LiveData<List<CatalogueEntity>> {
                val tvShowList = ArrayList<CatalogueEntity>()
                data.results.forEach {
                    val tvShow = CatalogueEntity(
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
                        isTopRated = true,
                    )
                    tvShowList.add(tvShow)
                }
                tvShowResult.postValue(tvShowList)
                return tvShowResult
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getTopTvShowList()
            }

        }.asLiveData()
    }



    override fun getTvShowDetail(tvShow: CatalogueEntity): LiveData<Resource<CatalogueEntity>> {
        return object : NetworkBoundResource<CatalogueEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<CatalogueEntity> {
                return localDataSource.getTvShowDetail(tvShow.id)
            }

            override fun shouldFetch(data: CatalogueEntity?): Boolean {
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

                        val tvShowDetail = CatalogueEntity(
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
                            isTopRated = tvShow.isTopRated,
                            isTvShow = true
                        )
                        localDataSource.updateTvShowData(tvShowDetail)
                    }
                }
            }
        }.asLiveData()
    }

    override fun getTvShowPlaylist(): LiveData<List<CatalogueEntity>> {
        return localDataSource.getTvShowPlaylist()
    }

    override fun setTvShowPlaylist(tvShow: CatalogueEntity, state: Boolean) {
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
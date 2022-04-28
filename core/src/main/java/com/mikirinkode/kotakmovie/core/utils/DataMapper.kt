package com.mikirinkode.kotakmovie.core.utils

import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakmovie.core.data.source.remote.response.*
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo

object DataMapper {
    /*
        Movie Response To Entity
     */
    fun mapMovieResponsesToEntities(data: MovieListResponse): List<CatalogueEntity> {
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
        return movieList
    }


    /*
       TV Response To Entity
     */
    fun mapTvResponsesToEntities(data: TvShowListResponse): List<CatalogueEntity> {
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
                isTvShow = true
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }


    /*
       Entities To Domain
     */
    private fun mapEntityToDomain(data: CatalogueEntity): Catalogue {
        return Catalogue(
            id = data.id,
            title = data.title,
            releaseDate = data.releaseDate,
            overview = data.overview,
            tagline = data.tagline,
            genres = data.genres,
            runtime = data.runtime,
            voteAverage = data.voteAverage,
            posterPath = data.posterPath,
            backdropPath = data.backdropPath,
            isOnPlaylist = data.isOnPlaylist,
            isTvShow = data.isTvShow
        )
    }

    fun mapEntitiesToDomain(data: List<CatalogueEntity>): List<Catalogue> {
        return data.map {
            mapEntityToDomain(it)
        }
    }


    /*
        Domain To Entities
    */
    fun mapDomainToEntity(data: Catalogue): CatalogueEntity {
        return CatalogueEntity(
            id = data.id,
            title = data.title,
            releaseDate = data.releaseDate,
            overview = data.overview,
            tagline = data.tagline,
            genres = data.genres,
            runtime = data.runtime,
            voteAverage = data.voteAverage,
            posterPath = data.posterPath,
            backdropPath = data.backdropPath,
            isOnPlaylist = data.isOnPlaylist,
            isTvShow = data.isTvShow
        )
    }

    /*
        Response To Domain
     */
    fun mapSearchResponseToDomain(data: SearchResponse): List<Catalogue> {
        return data.results.map {
            Catalogue(
                id = it.id,
                title = if(it.mediaType == "tv") it.name else it.title,
                releaseDate = if(it.mediaType == "tv") it.firstAirDate else it.releaseDate,
                isTvShow = it.mediaType == "tv",
                overview = it.overview,
                tagline = null,
                genres = null,
                runtime = null,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
        }
    }

    fun mapMovieResponsesToDomain(data: MovieListResponse): List<Catalogue> {
        return data.results.map {
            Catalogue(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                overview = it.overview,
                tagline = null,
                genres = null,
                runtime = null,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
        }
    }

    fun mapDetailMovieResponseToDomain(data: MovieDetailResponse): Catalogue {
        data.apply {
            val genreList = ArrayList<String>()
            genres.forEach { genreList.add(it.name) }
            val genre = genreList.joinToString(separator = ", ")
            return Catalogue(
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
            )
        }
    }

    fun mapTvResponsesToDomain(data: TvShowListResponse): List<Catalogue> {
        return data.results.map {
            Catalogue(
                id = it.id,
                title = it.name,
                releaseDate = it.firstAirDate,
                overview = it.overview,
                tagline = null,
                genres = null,
                runtime = null,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isTvShow = true
            )
        }
    }

    fun mapDetailTvResponseToDomain(data: TvShowDetailResponse): Catalogue {
        data.apply {
            val genreList = ArrayList<String>()
            genres.forEach { genreList.add(it.name) }
            val genre = genreList.joinToString(separator = ", ")
            return Catalogue(
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
                isTvShow = true
            )
        }
    }

    /*
    Trailer Response To Domain
    */
    fun mapTrailerResponseToDomain(data: TrailerVideoResponse): List<TrailerVideo> {
        val trailerList = ArrayList<TrailerVideo>()
        data.results.map {
            if (it.site == "YouTube" && it.type == "Trailer") {
                trailerList.add(
                    TrailerVideo(
                        it.key,
                        it.name
                    )
                )
            }
        }
        return trailerList
    }
}
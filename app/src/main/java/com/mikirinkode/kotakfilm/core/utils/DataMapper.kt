package com.mikirinkode.kotakfilm.core.utils

import com.mikirinkode.kotakfilm.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakfilm.core.data.entity.TrailerVideoEntity
import com.mikirinkode.kotakfilm.core.data.source.remote.response.*
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue

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

    fun mapDetailMovieResponseToEntity(data: MovieDetailResponse): CatalogueEntity {
        data.apply {
            val genreList = ArrayList<String>()
            genres.forEach { genreList.add(it.name) }
            val genre = genreList.joinToString(separator = ", ")
            return CatalogueEntity(
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

    /*
        Trailer To Entity
     */
    fun mapTrailerResponseToEntity(
        data: TrailerItem,
        catalogue: Catalogue
    ): TrailerVideoEntity {
        return TrailerVideoEntity(
            data.id,
            catalogue.id,
            data.key,
            data.name,
            data.site,
            data.type,
            data.official
        )
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

    fun mapDetailTvResponseToEntity(data: TvShowDetailResponse): CatalogueEntity {
        data.apply {
            val genreList = ArrayList<String>()
            genres.forEach { genreList.add(it.name) }
            val genre = genreList.joinToString(separator = ", ")
            return CatalogueEntity(
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
       Entities To Domain
     */
    fun mapEntityToDomain(data: CatalogueEntity?): Catalogue?{
        if (data == null){
            return null
        } else {
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
    }
    fun mapEntitiesToDomain(data: List<CatalogueEntity>): List<Catalogue>{
        return data.map {
            mapEntityToDomain(it)!!
        }
    }


    /*
        Domain To Entities
    */
    fun mapDomainToEntity(data: Catalogue): CatalogueEntity{
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
    fun mapMovieResponsesToDomain(data: MovieListResponse): List<Catalogue>{
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

    fun mapTvResponsesToDomain(data: TvShowListResponse): List<Catalogue>{
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
            )
        }
    }
}
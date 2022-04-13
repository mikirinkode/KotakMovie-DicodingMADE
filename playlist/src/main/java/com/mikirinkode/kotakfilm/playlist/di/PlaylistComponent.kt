package com.mikirinkode.kotakfilm.playlist.di

import android.content.Context
import com.mikirinkode.kotakfilm.di.PlaylistModuleDependencies
import com.mikirinkode.kotakfilm.playlist.ui.movie.MoviePlaylistFragment
import com.mikirinkode.kotakfilm.playlist.ui.tvshow.TvShowPlaylistFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [PlaylistModuleDependencies::class])
interface PlaylistComponent {

    fun inject(fragment: MoviePlaylistFragment)
    fun inject(fragment: TvShowPlaylistFragment)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(playlistModuleDependencies: PlaylistModuleDependencies): Builder
        fun build(): PlaylistComponent
    }
}
package com.mikirinkode.kotakmovie.playlist.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mikirinkode.kotakmovie.playlist.ui.movie.MoviePlaylistFragment
import com.mikirinkode.kotakmovie.playlist.ui.tvshow.TvShowPlaylistFragment

class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = MoviePlaylistFragment()
            1 -> fragment = TvShowPlaylistFragment()
        }
        return fragment as Fragment
    }


}
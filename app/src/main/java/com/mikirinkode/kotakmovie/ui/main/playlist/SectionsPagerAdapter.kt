package com.mikirinkode.kotakmovie.ui.main.playlist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mikirinkode.kotakmovie.ui.main.playlist.movie.MoviePlaylistFragment
import com.mikirinkode.kotakmovie.ui.main.playlist.tvshow.TvShowPlaylistFragment

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
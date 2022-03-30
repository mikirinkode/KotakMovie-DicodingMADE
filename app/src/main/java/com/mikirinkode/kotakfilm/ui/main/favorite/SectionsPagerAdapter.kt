package com.mikirinkode.kotakfilm.ui.main.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mikirinkode.kotakfilm.ui.main.favorite.movie.FavoriteMovieFragment
import com.mikirinkode.kotakfilm.ui.main.favorite.tvshow.FavoriteTvShowFragment

class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FavoriteMovieFragment()
            1 -> fragment = FavoriteTvShowFragment()
        }
        return fragment as Fragment
    }


}
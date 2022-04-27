package com.mikirinkode.kotakfilm.playlist.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.mikirinkode.kotakfilm.playlist.R
import com.mikirinkode.kotakfilm.playlist.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment(R.layout.fragment_playlist) {

    private val binding: FragmentPlaylistBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@PlaylistFragment)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            com.mikirinkode.kotakfilm.R.string.movie,
            com.mikirinkode.kotakfilm.R.string.tv_show
        )
    }
}
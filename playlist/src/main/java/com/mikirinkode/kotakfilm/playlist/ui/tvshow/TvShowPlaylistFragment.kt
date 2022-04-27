package com.mikirinkode.kotakfilm.playlist.ui.tvshow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mikirinkode.kotakfilm.core.ui.CatalogueAdapter
import com.mikirinkode.kotakfilm.di.PlaylistModuleDependencies
import com.mikirinkode.kotakfilm.playlist.R
import com.mikirinkode.kotakfilm.playlist.databinding.FragmentTvShowPlaylistBinding
import com.mikirinkode.kotakfilm.playlist.di.DaggerPlaylistComponent
import com.mikirinkode.kotakfilm.playlist.di.ViewModelFactory
import com.mikirinkode.kotakfilm.playlist.ui.PlaylistViewModel
import com.mikirinkode.kotakfilm.ui.detail.DetailCatalogueActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class TvShowPlaylistFragment : Fragment(R.layout.fragment_tv_show_playlist) {

    private val binding: FragmentTvShowPlaylistBinding by viewBinding()
    private val tvShowAdapter = CatalogueAdapter()

    @Inject
    lateinit var factory: ViewModelFactory

    private val playlistViewModel: PlaylistViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerPlaylistComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    activity?.applicationContext as Context,
                    PlaylistModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            with(binding.rvFilm){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
            tvShowAdapter.onItemClick = { selectedData ->
                val moveToDetail = Intent(requireContext(), DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_FILM, selectedData)
                startActivity(moveToDetail)
            }

            playlistViewModel.getTvShowPlaylist().observe(viewLifecycleOwner) { tvShowList ->
                tvShowAdapter.setData(tvShowList)
                if (tvShowList.isNotEmpty()) {
                    binding.apply { onEmptyData.visibility = View.GONE }
                } else {
                    binding.apply { onEmptyData.visibility = View.VISIBLE }
                }
            }
        }
    }
}
package com.mikirinkode.kotakmovie.ui.main.playlist.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikirinkode.kotakmovie.databinding.FragmentTvShowPlaylistBinding
import com.mikirinkode.kotakmovie.ui.detail.DetailCatalogueActivity
import com.mikirinkode.kotakmovie.ui.main.home.CatalogueAdapter
import com.mikirinkode.kotakmovie.ui.main.playlist.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowPlaylistFragment : Fragment() {


    private var _binding: FragmentTvShowPlaylistBinding? = null
    private val binding get() = _binding!!
    private val tvShowAdapter = CatalogueAdapter()
    private val playlistViewModel: PlaylistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowPlaylistBinding.inflate(inflater, container, false)
        return binding.root
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
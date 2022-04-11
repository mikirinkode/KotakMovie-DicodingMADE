package com.mikirinkode.kotakfilm.ui.main.playlist.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.databinding.FragmentTvShowPlaylistBinding
import com.mikirinkode.kotakfilm.ui.main.tvshow.TvShowAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TvShowPlaylistFragment : Fragment() {

    private var _binding: FragmentTvShowPlaylistBinding? = null
    private val binding get() = _binding!!
    private val tvShowAdapter = TvShowAdapter()
    private val playlistViewModel: TvShowPlaylistViewModel by viewModels()

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
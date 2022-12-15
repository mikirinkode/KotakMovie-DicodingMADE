package com.mikirinkode.kotakmovie.ui.main.playlist.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikirinkode.kotakmovie.databinding.FragmentMoviePlaylistBinding
import com.mikirinkode.kotakmovie.ui.detail.DetailCatalogueActivity
import com.mikirinkode.kotakmovie.ui.main.home.CatalogueAdapter
import com.mikirinkode.kotakmovie.viewmodel.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviePlaylistFragment : Fragment() {


    private var _binding: FragmentMoviePlaylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var catalogueAdapter: CatalogueAdapter

    private val playlistViewModel: PlaylistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            catalogueAdapter = CatalogueAdapter()

            with(binding.rvFilm){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = catalogueAdapter
            }
            catalogueAdapter.onItemClick = { selectedData ->
                val moveToDetail = Intent(requireContext(), DetailCatalogueActivity::class.java)
                moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_FILM, selectedData)
                startActivity(moveToDetail)
            }

            playlistViewModel.getMoviePlaylist().observe(viewLifecycleOwner) { movieList ->
                catalogueAdapter.setData(movieList)
                if (movieList.isNotEmpty()) {
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
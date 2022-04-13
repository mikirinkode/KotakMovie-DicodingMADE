package com.mikirinkode.kotakfilm.playlist.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikirinkode.kotakfilm.core.ui.CatalogueAdapter
import com.mikirinkode.kotakfilm.di.PlaylistModuleDependencies
import com.mikirinkode.kotakfilm.playlist.ui.PlaylistViewModel
import com.mikirinkode.kotakfilm.playlist.databinding.FragmentMoviePlaylistBinding
import com.mikirinkode.kotakfilm.playlist.di.DaggerPlaylistComponent
import com.mikirinkode.kotakfilm.playlist.di.ViewModelFactory
import com.mikirinkode.kotakfilm.ui.detail.DetailCatalogueActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class MoviePlaylistFragment : Fragment() {

    private var _binding: FragmentMoviePlaylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var catalogueAdapter: CatalogueAdapter

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
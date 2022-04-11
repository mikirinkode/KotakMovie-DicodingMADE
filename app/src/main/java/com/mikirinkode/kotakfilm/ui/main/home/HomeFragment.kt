package com.mikirinkode.kotakfilm.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikirinkode.kotakfilm.core.vo.Resource
import com.mikirinkode.kotakfilm.databinding.FragmentHomeBinding
import com.mikirinkode.kotakfilm.core.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val trendingAdapter = TrendingAdapter()
    private val movieAdapter = UpcomingMovieAdapter()
    private val tvShowAdapter = TopRatedTvAdapter()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.apply {
                rvUpcomingMovies.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = movieAdapter
                }

                findUpcomingMovies()

                rvTopTv.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = tvShowAdapter
                }

                findTopTvShows()

                rvTrending.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = trendingAdapter
                }

                findTrendingList()


                btnTryAgain.setOnClickListener {
                    findUpcomingMovies()
                    findTopTvShows()
                    findTrendingList()
                }
            }
        }
    }

    private fun findTrendingList() {
        binding.apply {
            loadingUpcomingMovie.visibility = View.VISIBLE
            onFailMsg.visibility = View.GONE
            viewModel.getTrendingMovies()
                .observe(viewLifecycleOwner) { movieList ->
                    if (movieList != null) {
                        when (movieList) {
                            is Resource.Loading -> {
                                loadingTrending.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                movieList.data?.let { trendingAdapter.setData(it) }
                                loadingTrending.visibility = View.GONE
                            }
                            is Resource.Error -> {
                                loadingTrending.visibility = View.GONE
                                onFailMsg.visibility = View.VISIBLE
                            }
                        }
                    }
                }
        }
    }

    private fun findUpcomingMovies() {
        binding.apply {
            loadingUpcomingMovie.visibility = View.VISIBLE
            onFailMsg.visibility = View.GONE
            viewModel.getUpcomingMovies()
                .observe(viewLifecycleOwner) { movieList ->
                    if (movieList != null) {
                        when (movieList) {
                            is Resource.Loading -> {
                                loadingUpcomingMovie.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                loadingUpcomingMovie.visibility = View.GONE
                                val sortedList = movieList.data?.sortedByDescending { it.releaseDate }
                                sortedList?.let { movieAdapter.setData(it) }
                            }
                            is Resource.Error -> {
                                loadingUpcomingMovie.visibility = View.GONE
                                onFailMsg.visibility = View.VISIBLE
                            }
                        }
                    }
                }
        }
    }

    private fun findTopTvShows() {
        binding.apply {
            loadingTopRatedTvShow.visibility = View.VISIBLE
            btnTryAgain.visibility = View.GONE
            onFailMsg.visibility = View.GONE
            viewModel.getTopTvShowList()
                .observe(viewLifecycleOwner) { tvShowList ->
                    if (tvShowList != null) {
                        when (tvShowList) {
                            is Resource.Loading -> {
                                loadingTopRatedTvShow.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                loadingTopRatedTvShow.visibility = View.GONE
                                tvShowList.data?.let { tvShowAdapter.setData(it) }
                            }
                            is Resource.Error -> {
                                loadingTopRatedTvShow.visibility = View.GONE
                                btnTryAgain.visibility = View.VISIBLE
                                onFailMsg.visibility = View.VISIBLE
                            }
                        }
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
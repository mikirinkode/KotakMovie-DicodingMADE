package com.mikirinkode.kotakfilm.ui.main.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.databinding.FragmentSearchBinding
import com.mikirinkode.kotakfilm.ui.main.movie.MovieAdapter
import com.mikirinkode.kotakfilm.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener{

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter = MovieAdapter()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        if (activity != null) {
            binding.apply {
                rvSearchResult.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = movieAdapter
                }
            }
            observeSearchResult()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        search.expandActionView()

        val searchView = search.actionView as SearchView
        searchView.apply {
            onActionViewExpanded()
            setIconifiedByDefault(true)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            requestFocus()
            setOnQueryTextListener(this@SearchFragment)
        }
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            binding.icLoading.visibility = View.VISIBLE
            searchViewModel.setSearchQuery(query)
            observeSearchResult()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true;
    }

    private fun observeSearchResult(){
        binding.apply {
            searchViewModel.searchResult.observe(viewLifecycleOwner, Observer { results ->
                if (results != null) {
//                    movieAdapter.setData(results)
                    when (results.status) {
                        Status.LOADING -> {
                            icLoading.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            results.data?.let { movieAdapter.setData(it) }
                            icLoading.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            icLoading.visibility = View.GONE
                            onFailMsg.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.mikirinkode.kotakmovie.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.databinding.FragmentSearchBinding
import com.mikirinkode.kotakmovie.core.ui.CatalogueAdapter
import com.mikirinkode.kotakmovie.core.vo.Resource
import com.mikirinkode.kotakmovie.ui.detail.DetailCatalogueActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val movieAdapter = CatalogueAdapter()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        if (activity != null) {
            binding.apply {
                rvSearchResult.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = movieAdapter
                }
                movieAdapter.onItemClick = { selectedData ->
                    val moveToDetail = Intent(requireContext(), DetailCatalogueActivity::class.java)
                    moveToDetail.putExtra(DetailCatalogueActivity.EXTRA_FILM, selectedData)
                    startActivity(moveToDetail)
                }
            }
            observeSearchResult()
        }
    }

    override fun onResume() {
        super.onResume()
        if (movieAdapter.itemCount > 0) {
            binding.onInitialSearchStateMessage.visibility = View.GONE
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
            setIconifiedByDefault(false)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            requestFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        binding.apply {
                            icLoading.visibility = View.VISIBLE
                            onEmptyStateMessage.visibility = View.GONE
                            onFailMsg.visibility = View.GONE
                            onInitialSearchStateMessage.visibility = View.GONE
                            rvSearchResult.smoothScrollToPosition(0)
                        }
                        searchViewModel.setSearchQuery(query)
                        observeSearchResult()
                    } else if (query.equals("")) {
                        movieAdapter.clearList()
                    }
                    return true
                }
            })
        }
    }


    private fun observeSearchResult() {
        binding.apply {
            searchViewModel.searchResult.observe(viewLifecycleOwner) { results ->
                if (results != null) {
                    when (results) {
                        is Resource.Loading -> {
                            icLoading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            val searchResult = results.data
                            icLoading.visibility = View.GONE
                            if (searchResult != null) {
                                movieAdapter.setData(searchResult)
                                if (searchResult.isEmpty()){
                                    onEmptyStateMessage.visibility = View.VISIBLE
                                    onInitialSearchStateMessage.visibility = View.GONE
                                }
                            }
                        }
                        is Resource.Error -> {
                            icLoading.visibility = View.GONE
                            onFailMsg.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}
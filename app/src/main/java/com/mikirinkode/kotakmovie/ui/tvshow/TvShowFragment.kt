package com.mikirinkode.kotakmovie.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.databinding.FragmentTvShowBinding
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.ui.CatalogueAdapter
import com.mikirinkode.kotakmovie.core.utils.SortUtils
import com.mikirinkode.kotakmovie.core.vo.Resource
import com.mikirinkode.kotakmovie.ui.detail.DetailCatalogueActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment(R.layout.fragment_tv_show) {

    private val binding: FragmentTvShowBinding by viewBinding()
    private val tvShowAdapter = CatalogueAdapter()
    private val viewModel: TvShowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (activity != null) {
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
            findTvShowList()

            binding.apply {
                btnTryAgain.setOnClickListener{
                    findTvShowList()

                }
            }
        }
    }


    private fun findTvShowList(){
        binding.apply {
            icLoading.visibility = View.VISIBLE
            btnTryAgain.visibility = View.GONE
            onFailMsg.visibility = View.GONE
            viewModel.getPopularTvShowsList(SortUtils.LATEST).observe(viewLifecycleOwner, tvShowObserver)
        }
    }

    private val tvShowObserver = Observer<Resource<List<Catalogue>>> { tvShowList ->
        binding.apply {
            if(tvShowList != null) {
                when (tvShowList){
                    is Resource.Loading -> {
                        icLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        icLoading.visibility = View.GONE
                        tvShowList.data?.let { tvShowAdapter.setData(it) }
                    }
                    is Resource.Error -> {
                        icLoading.visibility = View.GONE
                        btnTryAgain.visibility = View.VISIBLE
                        onFailMsg.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sorting_data_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_latest_release -> sort = SortUtils.LATEST
            R.id.action_oldest_release -> sort = SortUtils.OLDEST
            R.id.action_best_vote -> sort = SortUtils.BEST
            R.id.action_worst_vote -> sort = SortUtils.WORST
            R.id.action_random -> sort = SortUtils.RANDOM
        }
        binding.apply {
            viewModel.getPopularTvShowsList(sort).observe(viewLifecycleOwner, tvShowObserver)
        }
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }
}
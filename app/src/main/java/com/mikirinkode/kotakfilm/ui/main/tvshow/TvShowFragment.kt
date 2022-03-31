package com.mikirinkode.kotakfilm.ui.main.tvshow

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.databinding.FragmentTvShowBinding
import com.mikirinkode.kotakfilm.utils.SortUtils
import com.mikirinkode.kotakfilm.vo.Resource
import com.mikirinkode.kotakfilm.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val tvShowAdapter = TvShowAdapter()
    private val viewModel: TvShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (activity != null) {
            with(binding.rvFilm){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
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

    private val tvShowObserver = Observer<Resource<List<TvShowEntity>>> { tvShowList ->
        binding.apply {
            if(tvShowList != null) {
                when (tvShowList.status){
                    Status.LOADING -> {
                        icLoading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        icLoading.visibility = View.GONE
                        tvShowList.data?.let { tvShowAdapter.setData(it) }
                    }
                    Status.ERROR -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
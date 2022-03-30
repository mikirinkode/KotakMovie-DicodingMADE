package com.mikirinkode.kotakfilm.ui.main.favorite.tvshow

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
import com.mikirinkode.kotakfilm.databinding.FragmentFavoriteTvShowBinding
import com.mikirinkode.kotakfilm.ui.main.tvshow.TvShowAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteTvShowFragment : Fragment() {

    private var _binding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _binding!!
    private val tvShowAdapter = TvShowAdapter()
    private val viewModel: FavoriteTvShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFilm)

        if(activity != null){
            with(binding.rvFilm){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }

            viewModel.getFavoriteTvShowList().observe(viewLifecycleOwner) { tvShowList ->
                tvShowAdapter.submitList(tvShowList)
                if (tvShowList.isNotEmpty()) {
                    binding.apply { onEmptyData.visibility = View.GONE }
                } else {
                    binding.apply { onEmptyData.visibility = View.VISIBLE }
                }
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = tvShowAdapter.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavorite(tvShowEntity) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.apply {
                    setActionTextColor(ContextCompat.getColor(context, R.color.primary_500))
                    setTextColor(ContextCompat.getColor(context, R.color.light_200))
                    setBackgroundTint(ContextCompat.getColor(context, R.color.dark_400))
                    setAction(R.string.message_ok) {
                        tvShowEntity?.let { viewModel.setFavorite(tvShowEntity) }
                    }
                    show()
                }

            }
        }
    })
}
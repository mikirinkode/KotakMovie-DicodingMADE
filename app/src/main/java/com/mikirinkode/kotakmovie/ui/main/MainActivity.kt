package com.mikirinkode.kotakmovie.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikirinkode.kotakmovie.R
import com.mikirinkode.kotakmovie.core.vo.Resource
import com.mikirinkode.kotakmovie.databinding.ActivityMainBinding
import com.mikirinkode.kotakmovie.ui.main.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(toolbar)
            val navView: BottomNavigationView = navBottom
            val navController =
                navHostFragmentActivityHome.getFragment<NavHostFragment>().navController
            val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_movie, R.id.nav_tv_show, R.id.nav_playlist
            ).build()
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    private fun findUpcomingMovies() {
        binding.apply {
            viewModel.getUpcomingMovies()
                .observe(this@MainActivity) { movieList ->
                    if (movieList != null) {
                        when (movieList) {
                            is Resource.Loading -> {
                                Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Success -> {
                                Toast.makeText(this@MainActivity, "SUCCESS", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Error -> {
                                Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }
    }
}
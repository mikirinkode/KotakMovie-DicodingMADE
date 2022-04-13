package com.mikirinkode.kotakfilm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mikirinkode.kotakfilm.R
import com.mikirinkode.kotakfilm.databinding.ActivityMainBinding
import com.mikirinkode.kotakfilm.ui.home.HomeFragment
import com.mikirinkode.kotakfilm.ui.movie.MovieFragment
import com.mikirinkode.kotakfilm.ui.search.SearchFragment
import com.mikirinkode.kotakfilm.ui.tvshow.TvShowFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
            val navController = navHostFragment.navController
//            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setSupportActionBar(toolbar)
//            setupActionBarWithNavController(navController, appBarConfiguration)
            setupBottomNavMenu(navController)
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        setFragment(HomeFragment())
        val bottomNav = binding.navBottom
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setFragment(HomeFragment())
                R.id.nav_search -> setFragment(SearchFragment())
                R.id.nav_movie -> setFragment(MovieFragment())
                R.id.nav_tv_show -> setFragment(TvShowFragment())
                R.id.nav_playlist -> setFragment(supportFragmentManager.instantiate("com.mikirinkode.kotakfilm.playlist.ui.PlaylistFragment"))
                else -> setFragment(HomeFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun FragmentManager.instantiate(className: String): Fragment {
        return fragmentFactory.instantiate(ClassLoader.getSystemClassLoader(), className)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_home, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
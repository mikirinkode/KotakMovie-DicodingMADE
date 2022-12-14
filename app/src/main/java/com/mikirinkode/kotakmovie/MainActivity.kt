package com.mikirinkode.kotakmovie

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mikirinkode.kotakmovie.databinding.ActivityMainBinding
import com.mikirinkode.kotakmovie.ui.home.HomeFragment
import com.mikirinkode.kotakmovie.ui.movie.MovieFragment
import com.mikirinkode.kotakmovie.ui.search.SearchFragment
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import com.mikirinkode.kotakmovie.ui.tvshow.TvShowFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotakMovieTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    KotakMovieApp()
                }
            }
        }
//        binding.apply {
//            val navHostFragment =
//                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
//            val navController = navHostFragment.navController
//            setSupportActionBar(toolbar)
//            setupBottomNavMenu(navController)
//        }
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
                R.id.nav_playlist -> setFragment(supportFragmentManager.instantiate("com.mikirinkode.kotakmovie.playlist.ui.PlaylistFragment"))
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
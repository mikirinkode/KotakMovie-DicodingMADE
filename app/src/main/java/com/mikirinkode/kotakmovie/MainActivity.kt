package com.mikirinkode.kotakmovie

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.mikirinkode.kotakmovie.databinding.ActivityMainBinding
import com.mikirinkode.kotakmovie.ui.theme.KotakMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
//            setSupportActionBar(toolbar)
//            val navView: BottomNavigationView = navBottom
//            val navController =
//                navHostFragmentActivityHome.getFragment<NavHostFragment>().navController
//            val appBarConfiguration = AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_search, R.id.nav_movie, R.id.nav_tv_show, R.id.nav_playlist
//            ).build()
//            setupActionBarWithNavController(navController, appBarConfiguration)
//            navView.setupWithNavController(navController)
//        }
    }
}
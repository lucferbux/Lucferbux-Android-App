package com.lucferbux.lucferbux.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.navigation.NavigationBarView
import com.lucferbux.lucferbux.R
import com.lucferbux.lucferbux.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.activity_main.*

enum class WindowSizeClass { COMPACT, MEDIUM, EXPANDED }

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)

        setUpNavigation()
    }


    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        if(bottom_nav !== null) {
            NavigationUI.setupWithNavController(
                bottom_nav,
                navHostFragment!!.navController
            )
        } else if (sidebar !== null) {
            NavigationUI.setupWithNavController(
                sidebar as NavigationBarView,
                navHostFragment!!.navController
            )
        } else {
            NavigationUI.setupWithNavController(
                navigation_bar,
                navHostFragment!!.navController
            )
        }



    }
}

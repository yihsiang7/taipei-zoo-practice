package com.yihsiang.zoo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yihsiang.zoo.R
import com.yihsiang.zoo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
                as? NavHostFragment ?: return
        navController = navHostFragment.navController

        binding.toolbar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.zoo_venue) {
                binding.toolbar.navigationIcon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_ios_24)
            }
        }

        // FIXME: 每次返回 Fragment 都重建...
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
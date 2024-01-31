package com.info.mybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.info.mybook.databinding.ActivityMainBinding
import com.info.mybook.viewmodels.UserViewModel
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.info.mybook.fragments.Home
import com.info.mybook.models.User
import com.info.mybook.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private var doubleBackToExitPressedOnce = false
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavMenu(navController)
    }

    // Example function to navigate to the second fragment
    fun navigateToSecondFragment() {
        navController.navigate(R.id.profileFragment)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNav.setupWithNavController(navController)

        bottomNav.setOnNavigationItemSelectedListener  { item ->
            Toast.makeText(this@MainActivity, "${item.itemId}", Toast.LENGTH_SHORT).show()
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.cartFragment -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment = navHostFragment?.childFragmentManager?.primaryNavigationFragment

        // Check if the current fragment is HomeFragment
        if(currentFragment is Home) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                finishAffinity()
                return
            }
            this.doubleBackToExitPressedOnce = true
            AppUtils.exitappShowSnackbar(this, findViewById(android.R.id.content), "Press back again to exit")
            //Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            handler.postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000) // Adjust the time window for double-click as needed
        } else {
            // Handle the back press for other fragments or activities
            super.onBackPressed()
        }
    }

//    override fun onItemClick(user: User) {
//        // Handle item click
//        Toast.makeText(this, "Item clicked: ${user.name}", Toast.LENGTH_SHORT).show()
//    }
}
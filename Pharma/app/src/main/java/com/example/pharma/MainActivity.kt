package com.example.pharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.fragment)

        //Setting the navigation controller to Bottom Nav
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.identification-> hideBottomNav()
                R.id.inscription-> hideBottomNav()
                R.id.forgot-> hideBottomNav()
                else->showBottomNav()

            }
        }


    }
    private fun showBottomNav() {
        bottomNav.visibility = View.VISIBLE

    }
    private fun hideBottomNav() {
        bottomNav.visibility = View.GONE

    }

}

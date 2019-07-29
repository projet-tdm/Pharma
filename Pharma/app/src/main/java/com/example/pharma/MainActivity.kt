package com.example.pharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity



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
        /*bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map-> {
                    // Permission has already been granted
                    startActivity(intentFor<MapsActivity>())
                     startActivity<MapsActivity>(
                         "job" to 1,
                         "lat" to 35.680,
                         "long" to -0.639
                 )
                }
                R.id.pharmacies-> {
                     val fragment = Pharmacies()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment, fragment)
                    transaction.commit()

                }

            }
            return@setOnNavigationItemSelectedListener true
        }*/



    }
    private fun hideBottomNav() {
        bottomNav.visibility = View.GONE

    }

}

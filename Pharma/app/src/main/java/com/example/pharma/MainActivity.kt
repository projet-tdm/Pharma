package com.example.pharma

import android.content.Context
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
import java.security.MessageDigest


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
        val pref = this.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val con = pref.getBoolean("connected", false)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.identification-> hideBottomNav()
                R.id.inscription-> hideBottomNav()
                R.id.forgot-> hideBottomNav()
                R.id.forgot-> hideBottomNav()
                R.id.accueil-> hideBottomNav()
                R.id.pharmacies-> {        val con = pref.getBoolean("connected", false)
                                            showBottomNav()
                                            if (con==false)  bottomNav.menu.getItem(2).setVisible(false)


                }
                R.id.mapFragment-> {
                                            val con = pref.getBoolean("connected", false)
                                            showBottomNav()
                                            if (con==false)  bottomNav.menu.getItem(2).setVisible(false)

                }
                R.id.ville-> {
                                            val con = pref.getBoolean("connected", false)
                                            showBottomNav()
                                            if (con==false) bottomNav.menu.getItem(2).setVisible(false)

                }
                R.id.detailPharma-> {        val con = pref.getBoolean("connected", false)
                                                showBottomNav()
                                                if (con==false) bottomNav.menu.getItem(2).setVisible(false)

                }
                else->showBottomNav()

            }
        }


    }
    private fun showBottomNav() {

        bottomNav.visibility = View.VISIBLE
        bottomNav.menu.getItem(2).setVisible(true)



    }
    private fun hideBottomNav() {
        bottomNav.visibility = View.GONE

    }


}

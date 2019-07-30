package com.example.pharma


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_identification.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity

private const val MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 10
/**
 * A simple [Fragment] subclass.
 *
 */
class Identification : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_identification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        create_account_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_identification_to_inscription)
        }
        forgot_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_identification_to_forgot)
        }
        connexion_btn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_identification_to_pharmacies)

        }
    }
}

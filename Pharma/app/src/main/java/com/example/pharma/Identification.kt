package com.example.pharma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pharma.Entity.UserManager
import kotlinx.android.synthetic.main.fragment_identification.*

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
        connexion_btn.setOnClickListener {view ->
            val userManager = UserManager()
            userManager.identify(activity!!,view)

        }
    }


}
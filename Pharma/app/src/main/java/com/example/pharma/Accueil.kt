package com.example.pharma


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import kotlinx.android.synthetic.main.fragment_accueil.*


class Accueil : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // If the list of cities is null, load the list from DB



        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val con = pref.getBoolean("connected", false) // false: valeur par défaut
        button4.setOnClickListener{ view ->
            if (con==false) {
            MaterialDialog(context!!).show {
                message(R.string.connexion)
                positiveButton(R.string.ok) {
                    view.findNavController().navigate(R.id.action_accueil_to_identification)
                }
                onCancel {
                 }

            }
        }
            else {

                view?.findNavController()?.navigate(R.id.action_accueil_to_commandeFragment)
            }
        }

        button7.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_accueil_to_ville)
        }
        button8.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_accueil_to_mapFragment)
        }
    }
}

package com.example.pharma


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.MyModel
import com.example.pharma.Entity.villeModel
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import kotlinx.android.synthetic.main.fragment_ville.*
import org.jetbrains.anko.support.v4.find


class Ville : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ville, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val villeModel = ViewModelProviders.of(activity!!).get(villeModel::class.java)

        // If the list of cities is null, load the list from DB

        villeModel.loadData(activity!!)



        button3.setOnClickListener {
            val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)

            pref.edit {
                putString(
                    "ville"
                    , android_material_design_spinner.text.toString()
                )
                view?.findNavController()?.navigate(R.id.action_ville_to_pharmacies)

            }
        }
    }

}

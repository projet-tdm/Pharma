package com.example.pharma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_pharmacies.*

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

class Pharmacies : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacies, container, false)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm= ViewModelProviders.of(activity!!).get(MyModel::class.java)
        val adapter = CustomAdapterPharmacie(context!!,vm.list)
        listpharmacie.adapter = adapter
        listpharmacie.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->

            var bundle = Bundle()
            bundle.putInt("id",position)
            view?.findNavController()?.navigate(R.id.action_pharmacies_to_detailPharma,bundle)



        })

    }


}

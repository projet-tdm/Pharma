package com.example.pharma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_pharmacies.*

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.MyModel
import com.example.pharma.ListAdapter.CustomAdapterPharmacie


class Pharmacies : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacies, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm= ViewModelProviders.of(activity!!).get(MyModel::class.java)
        val adapter = CustomAdapterPharmacie(context!!, vm.list)
        listpharmacie.adapter = adapter
        listpharmacie.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->

            var bundle = Bundle()
            bundle.putInt("id",position)
            view?.findNavController()?.navigate(R.id.action_pharmacies_to_detailPharma,bundle)


        }

    }


}

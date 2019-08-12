package com.example.pharma

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import androidx.core.content.edit
import kotlinx.android.synthetic.main.fragment_pharmacies.*

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.MyModel
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast


class Pharmacies() : Fragment() {

    var adapter:CustomAdapterPharmacie?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_pharmacies, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)

        val ville = pref.getString("ville", "alger")
        val pharmaModel = ViewModelProviders.of(activity!!).get(MyModel::class.java)
        val searchView = find(R.id.searchView) as SearchView
        val con = pref.getBoolean("connected", false)
        if(con==false)
        {
            button6.visibility=View.GONE
        }
        else
        {
            button6.visibility=View.VISIBLE
            button6.setOnClickListener {
                val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)

                pref.edit {
                    putBoolean("connected"
                        ,false)


                }

                view?.findNavController()?.navigate(R.id.action_pharmacies_to_identification)
            }
        }
        // If the list of cities is null, load the list from DB
       /* if (pharmaModel.list==null)
        {*/
            pharmaModel.loadData(activity!!,ville)

       /* }
        else
        {
            adapter = CustomAdapterPharmacie(activity!!,pharmaModel.list!!)
            listpharmacie.adapter=adapter
        }*/
        listpharmacie.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->

            var bundle = Bundle()
            bundle.putInt("id",position)
            view?.findNavController()?.navigate(R.id.action_pharmacies_to_detailPharma,bundle)


        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val text = newText
                /*Call filter Method Created in Custom Adapter
                    This Method Filter ListView According to Search Keyword
                 */
                val adapter=listpharmacie.adapter as CustomAdapterPharmacie
                adapter.filter(text)
                return false
            }
        })

    }





}

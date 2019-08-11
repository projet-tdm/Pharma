package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.MyModel
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.ListAdapter.CommandeAdapter
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import kotlinx.android.synthetic.main.fragment_commande.*
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import java.util.ArrayList


class CommandeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commande, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val nss = arguments!!.getInt("nss")

        val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)

             cmdModel.loadData(activity!!,nss)

             //listcmd.adapter = CommandeAdapter(activity!!,cmdModel.list!!)




        add.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_formulaireCommande)
        }

    }

}

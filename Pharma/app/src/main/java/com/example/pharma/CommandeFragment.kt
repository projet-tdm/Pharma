package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.ListAdapter.CommandeAdapter
import kotlinx.android.synthetic.main.fragment_commande.*
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


        val adapter = CommandeAdapter(context!!, getData())
        listcmd.adapter = adapter
        add.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_formulaireCommande)
        }

    }
    private fun getData(): ArrayList<Commande> {
        val ouv= "hh"
        val fer= "hh"
        val pharma= Pharmacie(
            1,
            "cité 400 logement rue 24",
            "023",

            "conv",
            "facebok",
            "sétif",
            "hh",
            "hh",
                    "hh",1,"hi"
        )

        val cmd= Commande(1, R.drawable.ic_launcher_background, "T", pharma, "12/06/2019")

        val list = ArrayList<Commande>()

        list.add(cmd)
        list.add(cmd)
        list.add(cmd)
        list.add(cmd)
        list.add(cmd)
        list.add(cmd)
        list.add(cmd)
        list.add(cmd)




        return list

    }
}

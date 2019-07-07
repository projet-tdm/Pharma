package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_detail_pharma.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailPharma : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pharma, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm= ViewModelProviders.of(activity!!).get(MyModel::class.java)

        val amount = arguments!!.getInt("id")
        val pharmacie = vm.list.get(amount)
        textView22.text=pharmacie.adresse

        textView9.text=pharmacie.horaireOv.get(0)+"-"+pharmacie.horaireFerm.get(0)
        textView4.text=pharmacie.cassConv
        textView10.text=pharmacie.horaireOv.get(1)+" - "+pharmacie.horaireFerm.get(1)
        textView13.text=pharmacie.horaireOv.get(2)+" - "+pharmacie.horaireFerm.get(2)
        textView14.text=pharmacie.horaireOv.get(3)+" - "+pharmacie.horaireFerm.get(3)
        textView15.text=pharmacie.horaireOv.get(4)+" - "+pharmacie.horaireFerm.get(4)
        textView16.text=pharmacie.horaireOv.get(5)+" - "+pharmacie.horaireFerm.get(5)
        button5.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailPharma_to_commandeFragment)
        }

    }

}

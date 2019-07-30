package com.example.pharma


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_detail_pharma.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.MyModel
import kotlinx.android.synthetic.main.horairelayout.*

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
        textView21.text=pharmacie.nom
        textView4.text=pharmacie.cassConv
        textView9.text="Dimanche              "+ pharmacie.horaireOv.get(0)+" - "+pharmacie.horaireFerm.get(0)
        textView10.text="Lundi                     "+ pharmacie.horaireOv.get(1)+" - "+pharmacie.horaireFerm.get(1)
        textView13.text="Mardi                     " + pharmacie.horaireOv.get(2)+" - "+pharmacie.horaireFerm.get(2)
        textView14.text="Mercredi                "+ pharmacie.horaireOv.get(3)+" - "+pharmacie.horaireFerm.get(3)
        textView15.text="Jeudi                     "+ pharmacie.horaireOv.get(4)+" - "+pharmacie.horaireFerm.get(4)
        textView16.text="Vendredi               "+ pharmacie.horaireOv.get(5)+" - "+pharmacie.horaireFerm.get(5)
        textView17.text="Samedi                 "+ pharmacie.horaireOv.get(6)+" - "+pharmacie.horaireFerm.get(6)
        button.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailPharma_to_pharmacies)
        }
        phone.setOnClickListener {
            makeCall("0797221310")
        }
        fb.setOnClickListener {
            goToFacebook("ZOGHBIissam")
        }
        loc.setOnClickListener {
            goToMap("geo:0,0?q=-33.8666,151.1957(Google+Sydney)")
        }


    }
    private fun goToFacebook (id:String )
    {
        try{
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+id))
            startActivity(intent)

        }
        catch(e : ActivityNotFoundException )
        {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/"+id))
            startActivity(intent)
        }
    }
    private fun goToMap (id:String )
    {
        val gmmIntentUri = Uri.parse(id)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)

    }
    private fun makeCall (id:String )
    {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + id)//change the number
        startActivity(callIntent)

    }

}

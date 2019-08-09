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
        val pharmacie = vm.list?.get(amount)
        textView22.text=pharmacie?.adresse
        textView21.text=pharmacie?.nom
        textView4.text=pharmacie?.cassConv
        val str1 = pharmacie?.horaireOv?.split(",")?.toTypedArray()
        val strs2 = pharmacie?.horaireFerm?.split(",")?.toTypedArray()


        textView9.text="Dimanche              "+ str1?.get(0)+" - "+strs2?.get(0)
        textView10.text="Lundi                     "+ str1?.get(1)+" - "+strs2?.get(1)
        textView13.text="Mardi                     " + str1?.get(2)+" - "+strs2?.get(2)
        textView14.text="Mercredi                "+ str1?.get(3)+" - "+strs2?.get(3)
        textView15.text="Jeudi                     "+str1?.get(4)+" - "+strs2?.get(4)
        textView16.text="Vendredi               "+ str1?.get(5)+" - "+strs2?.get(5)
        textView17.text="Samedi                 "+ str1?.get(6)+" - "+strs2?.get(6)
        button.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailPharma_to_pharmacies)
        }
        phone.setOnClickListener {
            makeCall(pharmacie!!.tel)
        }
        fb.setOnClickListener {
            goToFacebook(pharmacie!!.facebook)
        }
        loc.setOnClickListener {
            goToMap(pharmacie!!.location)
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

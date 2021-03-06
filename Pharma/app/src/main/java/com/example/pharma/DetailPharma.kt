package com.example.pharma


import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import kotlinx.android.synthetic.main.fragment_detail_pharma.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.MyModel
import com.example.pharma.Entity.Pharmacie
import kotlinx.android.synthetic.main.fragment_formulaire_commande.*
import kotlinx.android.synthetic.main.horairelayout.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.toast

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
        var pharmacie :Pharmacie? = null
        val amount = arguments!!.getInt("id")
        if (arguments!!.getInt("map")==1) pharmacie = vm.listGarde?.get(amount)
        else pharmacie = vm.list?.get(amount)
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

        phone.setOnClickListener {
            makeCall(pharmacie!!.tel)
        }
        fb.setOnClickListener {
            goToFacebook(pharmacie!!.facebook)
        }
        loc.setOnClickListener {
            goToMap(pharmacie!!.lat,pharmacie!!.lng)
        }
        bac.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailPharma_to_pharmacies)
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
    private fun goToMap (lat:Double,lng:Double)
    {


        val pharma="Pharmacie"
        val gmmIntentUri = Uri.parse("geo:0,0?q=$lat,$lng($pharma)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)

    }
    private fun makeCall (id:String )
    {
        if (ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + id)//change the number
            startActivity(callIntent)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}

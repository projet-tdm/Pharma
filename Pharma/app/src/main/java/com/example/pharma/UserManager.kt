package com.example.pharma

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_identification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserManager {


    fun alterUser(nss:Int, passwd:String,new:Int,view:View,activity: FragmentActivity?){
        val renewObject = RenewObject(nss, passwd,new)
        val passCall = RetrofitService.usersEndpoint.alterUserPass(renewObject)
        passCall.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>?, response:
                Response<String>?
            ) {
                if (response?.isSuccessful!!) {
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                    view.findNavController().navigate(R.id.pharmacies)
                }
                else {
                    //Toast
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                //Toast
                Toast.makeText(activity, "Echec de la connexion au serveur ! Vérifiez votre connexion internet", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun addUser(user :User, view: View,activity:FragmentActivity?){
        val userCall = RetrofitService.usersEndpoint.addUser(user)
        userCall.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>?, response:
                Response<String>?
            ) {
                if (response?.isSuccessful!!) {
                    val rep: String = response.body()!!
                    //Dialog
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                    val smsManager = SMSManager()
                    smsManager.sendSms(user.mdp,user.tel, view,activity)
                } else {
                    //Toast
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                //Toast
                Toast.makeText(activity, "Echec de la connexion au serveur ! Vérifiez votre connexion internet", Toast.LENGTH_LONG).show()
            }
        })
    }
}
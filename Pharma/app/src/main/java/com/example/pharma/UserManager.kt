package com.example.pharma

import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.pharma.UsersRetrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class UserManager {


    fun alterUser(nss:Int, passwd:String,new:Int,view:View,activity: FragmentActivity?){
        val renewObject = RenewObject(nss, passwd.toMD5(),new)
        val passCall = RetrofitService.usersEndpoint.alterUserPass(renewObject)
        passCall.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>?, response:
                Response<String>?
            ) {
                if (response?.isSuccessful!!) {
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                    view.findNavController().navigate(R.id.identification)
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
        var userEnc = user.copy()
        userEnc.mdp = userEnc.mdp.toMD5()
        val userCall = RetrofitService.usersEndpoint.addUser(userEnc)
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
    fun String.toMD5(): String {
        // toByteArray: default is Charsets.UTF_8 - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/to-byte-array.html
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}
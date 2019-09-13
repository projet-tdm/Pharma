package com.example.pharma.Entity

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.pharma.R
import com.example.pharma.SMSManager
import com.example.pharma.Retrofits.UsersRetrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_identification.*
import kotlinx.android.synthetic.main.fragment_identification.phone_EditText
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import kotlin.random.Random

class UserManager {


    fun alterUser(nss:Int, passwd:String,new:Int,view:View,activity: FragmentActivity?){
        val renewObject = RenewObject(nss, passwd.toMD5(), new)
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


    fun addUser(user : User, view: View, activity:FragmentActivity?){
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
    fun renewPassWord (act:FragmentActivity,view:View)
    {
        val randomString = (1..8)
            .map { i -> Random.nextInt(0, 8) }
            .joinToString("")

        val cnxCall = RetrofitService.usersEndpoint.getUserByTel(act.phone_EditText.text.toString().toInt())
        cnxCall.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>?, response:
                Response<List<User>>?
            ) {
                if (response?.isSuccessful!!) {
                    if(response.body()!!.isNotEmpty()){
                        val user : User = response.body()!!.first()
                        val smsManager = SMSManager()
                        smsManager.sendSmsForgot(randomString,act.phone_EditText.text.toString().toInt(),view,act)
                        val userManager = UserManager()
                        userManager.alterUser(user.nss,randomString,1,view, act)
                    } else act.phone_input.error = "Identifiant et/ou mot de passe incorrectes"
                }
                else {
                    //Toast
                    Toast.makeText(act, response.body().toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                //Toast
                Toast.makeText(act, "Echec de la connexion au serveur ! Vérifiez votre connexion internet", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun identify(act: Activity,view: View)
    {

        val cnxCall = RetrofitService.usersEndpoint.getUserByTel(act.phone_EditText.text.toString().toInt())
        cnxCall.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>?, response:
                Response<List<User>>?
            ) {
                if (response?.isSuccessful!!) {
                    if(response.body()!!.isNotEmpty()){
                        val user : User = response.body()!!.first()
                        if (user.mdp == act.password_EditText.text.toString().toMD5()) {
                            if (user.new == 1) {
                                var bundle = bundleOf("nss" to user.nss)
                                view.findNavController().navigate(R.id.action_identification_to_renew, bundle)
                            } else {
                                val pref = act.getSharedPreferences("fileName", Context.MODE_PRIVATE)
                                pref.edit {
                                    putBoolean("connected"
                                        ,true)
                                    putInt("nss"
                                        ,user.nss)
                                }


                                val device=pref.getString("device","vide")
                                val call = com.example.pharma.Retrofits.UsersRetrofit.RetrofitService.usersEndpoint.addDevice(
                                    Device(id=device,user = user.nss)
                                )
                                call.enqueue(object : Callback<String> {
                                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                                        if (response?.isSuccessful!!) {


                                        } else {
                                            act.toast("Une erreur s'est produite1")
                                        }
                                    }

                                    override fun onFailure(call: Call<String>?, t: Throwable?) {

                                        act.toast("Une erreur s'est produite")
                                    }


                                })
                                view.findNavController().navigate(R.id.action_identification_to_commandeFragment)
                            }
                        } else act.phone_input.error = "Identifiant et/ou mot de passe incorrectes"
                    }
                    else act.phone_input.error = "Identifiant et/ou mot de passe incorrectes"
                } else {
                    //Toast
                    Toast.makeText(act, response.body().toString(),Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                Toast.makeText(act, "Echec de la connexion au serveur ! Vérifiez votre connexion internet", Toast.LENGTH_LONG).show()
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
package com.example.pharma

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.pharma.Entity.Device
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import com.example.pharma.RoomDataBase.RoomService
import com.example.pharma.UsersRetrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_identification.*
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class Identification : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_identification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        create_account_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_identification_to_inscription)
        }
        forgot_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_identification_to_forgot)
        }
        connexion_btn.setOnClickListener {view ->
            val cnxCall = RetrofitService.usersEndpoint.getUserByTel(phone_EditText.text.toString().toInt())
            cnxCall.enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>?, response:
                    Response<List<User>>?
                ) {
                    if (response?.isSuccessful!!) {
                        if(response.body()!!.isNotEmpty()){
                            val user : User = response.body()!!.first()
                            if (user.mdp == password_EditText.text.toString().toMD5()) {
                                if (user.new == 1) {
                                    var bundle = bundleOf("nss" to user.nss)
                                    view.findNavController().navigate(R.id.action_identification_to_renew, bundle)
                                } else {
                                     val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
                                    pref.edit {
                                        putBoolean("connected"
                                            ,true)
                                        putInt("nss"
                                            ,user.nss)
                                    }


                                    val device=pref.getString("device","vide")
                                    val call = com.example.pharma.UsersRetrofit.RetrofitService.usersEndpoint.addDevice(Device(id=device,user = user.nss))
                                    call.enqueue(object : Callback<String> {
                                        override fun onResponse(call: Call<String>?, response: Response<String>?) {
                                            if (response?.isSuccessful!!) {


                                            } else {
                                                toast("Une erreur s'est produite1")
                                            }
                                        }

                                        override fun onFailure(call: Call<String>?, t: Throwable?) {

                                            toast("Une erreur s'est produite")
                                        }


                                    })
                                     view.findNavController().navigate(R.id.action_identification_to_commandeFragment)
                                }
                            } else phone_input.error = "Identifiant et/ou mot de passe incorrectes"
                        }
                        else phone_input.error = "Identifiant et/ou mot de passe incorrectes"
                    } else {
                        //Toast
                        Toast.makeText(activity, response.body().toString(),Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                    Toast.makeText(activity, "Echec de la connexion au serveur ! Vérifiez votre connexion internet", Toast.LENGTH_LONG).show()
                }
            })

        }
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

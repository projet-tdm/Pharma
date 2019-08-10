package com.example.pharma

import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.example.pharma.UsersRetrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SMSManager {
    fun sendSms(randomString: String, tel:Int, view: View,activity:FragmentActivity?){
        val call = RetrofitService.endpoint.sendSms(randomString,tel)
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>?, response:
                Response<String>?
            ) {
                if (response?.isSuccessful!!) {
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                    MaterialDialog(activity!!).show {
                        message(R.string.signup_btn_msg)
                        positiveButton(R.string.ok) {
                            view.findNavController().navigate(R.id.action_inscription_to_identification)
                        }
                        onCancel {
                            view.findNavController().navigate(R.id.action_inscription_to_identification)
                        }
                    }
                } else {
                    Toast.makeText(activity, "Le SMS n'a pas pu être envoyé ", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Toast.makeText(
                    activity,
                    "Echec de la connexion au serveur ! Vérifiez votre connexion internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun sendSmsForgot(randomString: String, tel:Int, view: View,activity:FragmentActivity?){
        val call = RetrofitService.endpoint.sendSms(randomString,tel)
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>?, response:
                Response<String>?
            ) {
                if (response?.isSuccessful!!) {
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()

                    MaterialDialog(activity!!).show {
                        message(R.string.signup_btn_msg)
                        onCancel {
                        }
                    }
                } else {
                    Toast.makeText(activity, "Le SMS n'a pas pu être envoyé ", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Toast.makeText(
                    activity,
                    "Echec de la connexion au serveur ! Vérifiez votre connexion internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
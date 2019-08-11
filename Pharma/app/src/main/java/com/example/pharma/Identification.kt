package com.example.pharma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.pharma.UsersRetrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_identification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                            if (user.mdp == password_EditText.text.toString()) {
                                if (user.new == 1) {
                                    var bundle = bundleOf("nss" to user.nss)
                                    view.findNavController().navigate(R.id.action_identification_to_renew, bundle)
                                } else {
                                    var bundle = Bundle()
                                    bundle.putInt("nss",user.nss)
                                    view.findNavController().navigate(R.id.action_identification_to_commandeFragment,bundle)


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
}

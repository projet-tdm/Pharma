package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pharma.UsersRetrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_forgot.*
import kotlinx.android.synthetic.main.fragment_forgot.phone_EditText
import kotlinx.android.synthetic.main.fragment_identification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class forgot : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        renew_password_btn.setOnClickListener{view ->

            val randomString = (1..8)
                .map { i -> Random.nextInt(0, 8) }
                .joinToString("")

            val cnxCall = RetrofitService.usersEndpoint.getUserByTel(phone_EditText.text.toString().toInt())
            cnxCall.enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>?, response:
                    Response<List<User>>?
                ) {
                    if (response?.isSuccessful!!) {
                        if(response.body()!!.isNotEmpty()){
                            val user : User = response.body()!!.first()
                            val smsManager = SMSManager()
                            smsManager.sendSmsForgot(randomString,phone_EditText.text.toString().toInt(),view,activity)
                            val userManager = UserManager()
                            userManager.alterUser(user.nss,randomString,1,view, activity)
                            } else phone_input.error = "Identifiant et/ou mot de passe incorrectes"
                        }
                     else {
                        //Toast
                        Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                    //Toast
                    Toast.makeText(activity, "Echec de la connexion au serveur ! Vérifiez votre connexion internet", Toast.LENGTH_LONG).show()
                }
            })

        }
    }
}

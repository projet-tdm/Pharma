package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_identification.*
import kotlinx.android.synthetic.main.fragment_renew.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class renew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renew, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        renew_passwd_btn.setOnClickListener{view ->
            val passwd = renew_passwd_EditText.text.toString()
            val conf_passwd = confirm_passwd_EditText.text.toString()
            val nss = arguments!!.getInt("nss")
            if(passwd == conf_passwd){
                //alter user
                val userManager = UserManager()
                userManager.alterUser(nss,passwd,0,view, activity)
            }else{
                renew_mdp_input.error = "Les mots de passe ne correspondent pas"
            }
        }
    }


}

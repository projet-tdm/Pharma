package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_inscription.*
import kotlin.random.Random

class Inscription : Fragment() {
    fun passwdGen ():String {
        val randomString = (1..8)
            .map { i -> Random.nextInt(0, 8) }
            .joinToString("")
        return randomString
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inscription, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signin_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_inscription_to_identification)
        }
        signup_btn.setOnClickListener { view ->
            val randomString = passwdGen()
            val user = User(
                nss_EditText.text.toString().toInt(),
                last_name_EditText.text.toString(),
                first_name_EditText.text.toString(),
                address_EditText.text.toString(),
                phone_EditText.text.toString().toInt(),
                randomString, 1
            )
            val userManager = UserManager()
            userManager.addUser(user, view, activity)
        }
    }
}

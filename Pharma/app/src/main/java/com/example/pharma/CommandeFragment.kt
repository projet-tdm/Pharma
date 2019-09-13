package com.example.pharma


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.Volley
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.NotificationModel
import com.example.pharma.ListAdapter.CommandeAdapter
import com.google.android.material.chip.Chip
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.cmdlayout.*
import kotlinx.android.synthetic.main.fragment_commande.*
import kotlinx.android.synthetic.main.fragment_formulaire_commande.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.runOnUiThread
import java.lang.Exception

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

import com.example.pharma.Entity.*




class CommandeFragment : Fragment() {
    internal var token:String = ""
    internal var paramsHash:HashMap<String,String>? = null
    var amount:String = ""
    var curentCmd : Commande? = null
    var curentCmdView : View? = null

    companion object{

        val  API_GET_TOKEN = paymentURL+"/client_token"
        val  API_CHECKOUT = paymentURL+"/checkout"
        val REQUEST_CODE:Int = 7777

    }
    fun getToken(cmd:Commande,view: View) {
        val androidClient = AsyncHttpClient()
        androidClient.get(API_GET_TOKEN,object : TextHttpResponseHandler(){

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                runOnUiThread {
                    token = responseString!!
                    doTransaction(cmd,view)
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                runOnUiThread {
                    Toast.makeText(ctx, "Failed to get token", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                val result = data!!.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT)
                val nonce = result.paymentMethodNonce
                val stringNonce = nonce!!.nonce

                if (!amount.isEmpty()){
                    paramsHash = HashMap()
                    paramsHash!!["amount"] = amount
                    paramsHash!!["nonce"] = stringNonce
                    sendPayments()
                }
                else Toast.makeText(ctx, "Please enter valid amount", Toast.LENGTH_LONG).show()
            }
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(ctx, "User cancel", Toast.LENGTH_LONG).show()
            else{
                val error =  data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                Log.d("Sofiane",error.message)
            }
        }
    }

    private fun sendPayments() {
        val queue = Volley.newRequestQueue(activity)



        val stringRequest = object: StringRequest(Request.Method.POST, API_CHECKOUT,
            Response.Listener { response ->
                if (response.toString().contains("Successful")){
                    var cmd : Commande = curentCmd!!
                    var view : View = curentCmdView!!
                    val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)
                    cmdModel.payer(ctx,curentCmd!!.numero,cmd,view)
                }
                else Toast.makeText(activity, "Transaction Failed ", Toast.LENGTH_LONG).show()
                Log.d("Resultat",response.toString())
            }, Response.ErrorListener { error ->
                Log.d("Erreur",error.message.toString())
            })
        {

            override fun getParams(): MutableMap<String, String> {
                /*if (paramsHash == null)
                    return null*/
                var params = HashMap<String,String>()
                for (key in paramsHash!!.keys){
                    Log.d("key",key.toString())
                    Log.d("value",paramsHash!![key].toString())
                    params.set(key,paramsHash!![key]!!)}
                Log.d("params",params.toString())
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["Content-Type"] = "applcation/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(stringRequest)
    }

     fun doTransaction(cmd:Commande,view: View){
         curentCmd = cmd
         curentCmdView = view
        val dropInRequest = DropInRequest().clientToken(token)
        startActivityForResult(dropInRequest.getIntent(activity),REQUEST_CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commande, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val nss = pref.getInt("nss", 0)
        val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)
        cmdModel.frag = this
        cmdModel.loadData(activity!!,nss)
        val notifModel = ViewModelProviders.of(activity!!).get(NotificationModel::class.java)

        notifModel.loadData(activity!!,nss)
        chip3.setOnClickListener {
            var ls:ArrayList<Commande>?= ArrayList()
            if(cmdModel.list!!.isEmpty()) order.visibility = View.VISIBLE
            for (e in cmdModel.list!!)
            {
                when(e.etat)
                {"A"-> ls!!.add(e) }

            }
            listcmd.adapter = CommandeAdapter(activity!!, ls!!,this)
        }
        chip4.setOnClickListener {
            var ls:ArrayList<Commande>?= ArrayList()
            for (e in cmdModel.list!!)
            {
                when(e.etat)
                {"T"->ls!!.add(e)}

            }
            listcmd.adapter = CommandeAdapter(activity!!, ls!!,this)
        }
        chip.setOnClickListener {
            var ls:ArrayList<Commande>?= ArrayList()
            for (e in cmdModel.list!!)
            {
                when(e.etat)
                {"C"->ls!!.add(e)}

            }
            listcmd.adapter = CommandeAdapter(activity!!, ls!!,this)
        }
        chip5.setOnClickListener {
            var ls:ArrayList<Commande>?= ArrayList()
            for (e in cmdModel.list!!)
            {
                when(e.etat)
                {"P"->ls!!.add(e)}

            }
            listcmd.adapter = CommandeAdapter(activity!!, ls!!,this)
        }




        add.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_formulaireCommande)
        }
        button5.setOnClickListener {
            val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
            pref.edit {
                putBoolean("connected",false)
            }
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_identification)
        }
        val button = view?.findViewById<Button>(R.id.notif)
        button?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_notifications)
        }
    }

}

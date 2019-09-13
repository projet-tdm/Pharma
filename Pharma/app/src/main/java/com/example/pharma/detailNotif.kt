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
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.Volley
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.Notification
import com.example.pharma.Entity.NotificationModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_detail_notif.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import java.lang.Exception


class detailNotif : Fragment() {
    internal var token:String = ""
    internal var paramsHash:HashMap<String,String>? = null
    var amount1:String = ""
    var curentCmd : Commande? = null
    var curentCmdView : View? = null

    companion object{
        val  API_GET_TOKEN = "http://ca64c3ab.ngrok.io/client_token"
        val  API_CHECKOUT = "http://ca64c3ab.ngrok.io/checkout"
        val REQUEST_CODE:Int = 7777

    }
    private fun getToken() {
        val androidClient = AsyncHttpClient()
        androidClient.get(API_GET_TOKEN,object : TextHttpResponseHandler(){

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                runOnUiThread {
                    token = responseString!!
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

                if (!amount1.isEmpty()){
                    paramsHash = HashMap()
                    paramsHash!!["amount"] = amount1
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
                    cmdModel.payerNotif(ctx,curentCmd!!.numero,cmd,view)
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
    fun doTransaction(cmd: Commande?, view: View){
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
        return inflater.inflate(R.layout.fragment_detail_notif, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm= ViewModelProviders.of(activity!!).get(NotificationModel::class.java)
        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)

        val nss = pref.getInt("nss", 0)
        vm.loadData(activity!!,nss)
        var notif : Notification? = null


        val d = pref.getInt("decision", 0)
        if (d==1) {
            val amount = pref.getInt("notif", 0)
            notif=vm.list!!.get(amount)
        }
        if(d==0)
        {   val amount=pref.getInt("notif", 0)
            notif=vm.list?.get(vm.notifById(amount))
        }
       // toast(""+amount)
       // notif=vm.list!!.get(vm.notifById(amount))
        if (notif?.vue==1) vm.setVue(activity!!,notif!!.id)
        textView28.text=notif?.date.toString()
        textView26.text="Commande  "+notif?.commande.toString()
        textView25.text="Pharmacie "+notif?.pharmacie
        textView27.text="Date de lancement "+notif?.dateL
        textView30.text="Notification "+notif?.id.toString()
        val mnt="33"
        textView29.text="Montant "+mnt+" DA"

         getToken()

        val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)
        button10.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailNotif_to_notifications)
        }
        button9.setOnClickListener {
            amount1 = mnt.toString()
            cmdModel.getCommande(activity!!,notif?.commande!!)
            toast(""+cmdModel.cmd?.etat)
            val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
            val nss = pref.getInt("nss", 0)
            when (cmdModel.cmd?.etat) {
                "T"->{doTransaction(cmdModel.cmd,view!!)
                    view?.findNavController()?.navigate(R.id.action_detailNotif_to_notifications)}

                "P"-> {
                MaterialDialog(context!!).show {
                    message(R.string.pay)
                    positiveButton(R.string.ok) {
                        view?.findNavController()?.navigate(R.id.action_detailNotif_to_notifications)}

                }



            }
        }

     }
}}

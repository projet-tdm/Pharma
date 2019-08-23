package com.example.pharma


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.content.Intent.getIntent
import com.braintreepayments.api.dropin.DropInRequest
import kotlinx.android.synthetic.main.fragment_payment.*
import android.R.attr.keySet
import android.app.Activity
import android.util.Log
import com.android.volley.*
import retrofit2.http.POST
//import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.app.ProgressDialog
import android.content.Intent
import com.braintreepayments.api.interfaces.HttpResponseCallback
import android.os.AsyncTask
import com.android.volley.request.StringRequest
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInResult
import com.braintreepayments.api.internal.HttpClient
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.client.ResponseHandler
import org.jetbrains.anko.support.v4.runOnUiThread
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap


class PaymentFragment : Fragment() {

    internal lateinit var token:String
    internal lateinit var amount:String
    internal var paramsHash:HashMap<String,String>? = null

    companion object{
        val  API_GET_TOKEN = "http://48fe37be.ngrok.io/client_token"
        val  API_CHECKOUT = "http://48fe37be.ngrok.io/checkout"
        val REQUEST_CODE:Int = 7777
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getToken()
        pay_btn.setOnClickListener{
            val dropInRequest = DropInRequest().clientToken(token)
            startActivityForResult(dropInRequest.getIntent(activity),REQUEST_CODE)
        }
    }

    private fun getToken() {
        val androidClient = AsyncHttpClient()
        androidClient.get(API_GET_TOKEN,object :TextHttpResponseHandler(){

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
                    Toast.makeText(activity, "Failed to get token",Toast.LENGTH_LONG).show()
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

                if (!amount_EditText.text.toString().isEmpty()){
                    amount = amount_EditText.text.toString()
                    paramsHash = HashMap()
                    paramsHash!!["amount"] = amount
                    paramsHash!!["nonce"] = stringNonce
                    sendPayments()
                }
                else Toast.makeText(activity, "Please enter valid amount",Toast.LENGTH_LONG).show()
            }
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(activity, "User cancel",Toast.LENGTH_LONG).show()
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
                if (response.toString().contains("Successful"))
                    Toast.makeText(activity, "Transaction Successful",Toast.LENGTH_LONG).show()
                else Toast.makeText(activity, "Transaction Failed ",Toast.LENGTH_LONG).show()
                Log.d("Resultat",response.toString())
            },Response.ErrorListener { error ->
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
}

package com.example.pharma.Entity
import android.app.Activity
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.pharma.ListAdapter.CommandeAdapter
 import com.example.pharma.Retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_commande.*
import kotlinx.android.synthetic.main.fragment_formulaire_commande.*
 import okhttp3.ResponseBody
 import org.jetbrains.anko.toast
import org.joda.time.LocalDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import android.R.attr.bitmap
import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.example.pharma.CommandeFragment
import com.example.pharma.R
import com.google.android.material.chip.Chip


class CmdModel: ViewModel() {


    var list:List<Commande>? = null
    var frag:CommandeFragment? = null
    var cmd:Commande?=null

    fun loadData(act: Activity,nss:Int) {
        act.progressBar2.visibility = View.VISIBLE
        getCommandesFromRemote(act,nss)

    }

    private fun getCommandesFromRemote(act:Activity,nss:Int) {
        val call = RetrofitService.endpoint.getCommandes(nss)
        call.enqueue(object : Callback<List<Commande>> {
            override fun onResponse(call: Call<List<Commande>>?, response: Response<List<Commande>>?) {
                act.progressBar2.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    list = response?.body()
                    act.progressBar2.visibility = View.GONE
                    act.listcmd.adapter = CommandeAdapter(act, list!!,frag!!)
                  } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Commande>>?, t: Throwable?) {
                act.progressBar2.visibility = View.GONE
                act.toast("Une erreur s'est produite")
            }


        })
    }
    private fun ImageToString(bitmap: Bitmap):String
    {
        var str = ByteArrayOutputStream()
        var bitmapC=getResizedBitmap(bitmap,700)
        bitmapC.compress(Bitmap.CompressFormat.JPEG,100,str)
        return Base64.encodeToString(str.toByteArray(), Base64.DEFAULT)


    }
    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        }
        else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

     fun addCommande(myBitmap: Bitmap,act: Activity,nss:Int,name:String) {


        var filestr =ImageToString(myBitmap)
        val current = LocalDateTime.now()
        val call = RetrofitService.endpointUpload.addCmd(filestr,"C",act.android_material_design_spinner1.selectedItem.toString(),current.toString(),nss,name)
        call.enqueue(object : Callback<MyResponse>{
            override fun onResponse(call: Call<MyResponse> ?, response: Response<MyResponse> ?) {
                if (response?.isSuccessful!!) {
                    act.toast("l'ordonnance est sauvgardé")
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<MyResponse> ?, t: Throwable?) {
                act.toast("Une erreur s'est produite")
            }


        })
    }
    fun annuler(act: Activity,id:Int)
    {
        val call = RetrofitService.endpoint.annulerCmd(id)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody> ?, response: Response<ResponseBody> ?) {
                if (response?.isSuccessful!!) {
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<ResponseBody> ?, t: Throwable?) {
                act.toast("Une erreur s'est produite")
            }


        })
    }
    fun payer(act: Context,id:Int,curentCmd:Commande,view:View)
    {
        val call = RetrofitService.endpoint.payerCmd(id)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody> ?, response: Response<ResponseBody> ?) {
                if (response?.isSuccessful!!) {
                    var curent = view?.findViewById(R.id.mnt) as TextView
                    curent.visibility = View.GONE
                    curent = view?.findViewById(R.id.payer) as TextView
                    curent.visibility = View.GONE
                    curent = view?.findViewById(R.id.chip2) as Chip
                    curent.setChipBackgroundColorResource(R.color.colorAccent)
                    Toast.makeText(act, "Transaction Successful", Toast.LENGTH_LONG).show()
                    curentCmd?.etat = "P"
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<ResponseBody> ?, t: Throwable?) {
                act.toast("Une erreur s'est produite 2")
            }
        })
    }
    fun payerNotif(act: Context,id:Int,curentCmd:Commande,view:View)
    {
        val call = RetrofitService.endpoint.payerCmd(id)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody> ?, response: Response<ResponseBody> ?) {
                if (response?.isSuccessful!!) {

                    Toast.makeText(act, "Transaction Successful", Toast.LENGTH_LONG).show()
                    curentCmd?.etat = "P"
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<ResponseBody> ?, t: Throwable?) {
                act.toast("Une erreur s'est produite 2")
            }
        })
    }
    fun getCommande(act: Activity,id:Int)
    {

        val call = RetrofitService.endpoint.getCommande(id)
        call.enqueue(object : Callback<List<Commande>>{
            override fun onResponse(call: Call<List<Commande>> ?, response: Response<List<Commande>> ?) {
                if (response?.isSuccessful!!) {
                    cmd=response?.body()?.first()
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Commande>> ?, t: Throwable?) {
                act.toast("Une erreur s'est produite cmd")
            }


        })

    }

}
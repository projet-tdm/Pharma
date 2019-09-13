package com.example.pharma.Entity

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
 import com.example.pharma.Retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_commande.*
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationModel: ViewModel() {
    var list:List<Notification>? = null
    var nbN:Int=0


    fun loadData(act: Activity, nss:Int) {
       getNotifFromRemote(act,nss)



    }
    fun setVue(act: Activity,id:Int)
    {
        val call = RetrofitService.endpoint.setVue(id)
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
     fun notifById(id:Int):Int
    {
        var find:Int=0
        for (e in list!!)
        {
            if (e.id==id) find=list!!.indexOf(e)
        }
        return find
    }
    private fun getNotifFromRemote(act: Activity, nss:Int) {
        val call = RetrofitService.endpoint.getNotif(nss)
        call.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(call: Call<List<Notification>>?, response: Response<List<Notification>>?) {
                  if (response?.isSuccessful!!) {
                    list = response?.body()
                      if(act.nbn!=null) {
                          nbN=0
                          for (e in list!!) {
                              if (e.vue == 1) nbN++
                          }

                          act.nbn.text = nbN.toString()
                      }
                } else {
                    act.toast("Une erreur s'est produite1")
                      act.nbn.text=nbN.toString()
                }
            }

            override fun onFailure(call: Call<List<Notification>>?, t: Throwable?) {
                act.progressBar2.visibility = View.GONE
                act.nbn.text=nbN.toString()
                act.toast("Une erreur s'est produite")
            }


        })
    }
}
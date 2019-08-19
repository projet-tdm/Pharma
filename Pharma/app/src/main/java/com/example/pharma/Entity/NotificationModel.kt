package com.example.pharma.Entity

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
 import com.example.pharma.Retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_commande.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationModel: ViewModel() {
    var list:List<Notification>? = null


    fun loadData(act: Activity, nss:Int) {
         getNotifFromRemote(act,nss)


    }

    private fun getNotifFromRemote(act: Activity, nss:Int) {
        val call = RetrofitService.endpoint.getNotif(nss)
        call.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(call: Call<List<Notification>>?, response: Response<List<Notification>>?) {
                  if (response?.isSuccessful!!) {
                    list = response?.body()

                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Notification>>?, t: Throwable?) {
                act.progressBar2.visibility = View.GONE
                act.toast("Une erreur s'est produite")
            }


        })
    }
}
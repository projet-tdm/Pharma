package com.example.pharma.Entity

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import com.example.pharma.Pharmacies
import com.example.pharma.Retrofit.RetrofitService
import com.example.pharma.RoomDataBase.RoomService
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyModel: ViewModel() {


    var list:ArrayList<Pharmacie>? = null
    var listGarde:ArrayList<Pharmacie>? = null


    fun loadData(act: Activity,ville:String)
    {
        act.progressBar.visibility = View.VISIBLE
        list = ArrayList(RoomService.appDataBase.getPharamcieDao().getPharmacies(ville))

        if (list?.size == 0)
        {
            getPharmaciesFromRemote(act,ville)

        }
        else
        {
            act.progressBar.visibility = View.GONE
            act.listpharmacie.adapter = CustomAdapterPharmacie(act, list!!)
        }
    }


    private fun getPharmaciesFromRemote(act:Activity,ville:String) {
        val call = RetrofitService.endpoint.getPharmacies(ville)
        call.enqueue(object : Callback<ArrayList<Pharmacie>> {
            override fun onResponse(call: Call<ArrayList<Pharmacie>>?, response: Response<ArrayList<Pharmacie>>?) {
                act.progressBar.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    list = response?.body()
                   act.progressBar.visibility = View.GONE
                    act.listpharmacie.adapter =CustomAdapterPharmacie(act,list!!)

                    RoomService.appDataBase.getPharamcieDao().addPharmacies(list!!)
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<ArrayList<Pharmacie>>?, t: Throwable?) {
               act.progressBar.visibility = View.GONE
                act.toast("Une erreur s'est produite")
            }


        })
    }

}
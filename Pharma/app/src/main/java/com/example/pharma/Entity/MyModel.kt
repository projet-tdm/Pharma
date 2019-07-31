package com.example.pharma.Entity
import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import com.example.pharma.Retrofit.RetrofitService
import com.example.pharma.RoomDataBase.RoomService
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyModel: ViewModel() {


    var pharma: Pharmacie? = null
    var list:List<Pharmacie>? = null


    fun loadData(act: Activity) {
       // act.progressBar1.visibility = View.VISIBLE
        // Get cities from SQLite DB
        list = RoomService.appDataBase.getPharamcieDao().getPharmacies()

        if (list?.size == 0) {
            // If the list of cities is empty, load from server and save them in SQLite DB
            getPharmaciesFromRemote(act)
        }
        else {
            //act.progressBar1.visibility = View.GONE
            act.listpharmacie.adapter = CustomAdapterPharmacie(act, list!!)
        }




    }

    private fun getPharmaciesFromRemote(act:Activity) {
        val call = RetrofitService.endpoint.getPharmacies()
        call.enqueue(object : Callback<List<Pharmacie>> {
            override fun onResponse(call: Call<List<Pharmacie>>?, response: Response<List<Pharmacie>>?) {
                //act.progressBar1.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    list = response?.body()
                   // act.progressBar1.visibility = View.GONE
                    act.listpharmacie.adapter = CustomAdapterPharmacie(act, list!!)
                    // save cities in SQLite DB
                    RoomService.appDataBase.getPharamcieDao().addPharmacies(list!!)
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Pharmacie>>?, t: Throwable?) {
               // act.progressBar1.visibility = View.GONE
                act.toast("Une erreur s'est produite")
            }


        })
    }

}
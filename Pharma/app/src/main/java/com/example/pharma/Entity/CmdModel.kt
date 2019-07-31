package com.example.pharma.Entity
import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.pharma.ListAdapter.CommandeAdapter
import com.example.pharma.ListAdapter.CustomAdapterPharmacie
import com.example.pharma.Retrofit.RetrofitService
import com.example.pharma.RoomDataBase.RoomService
import kotlinx.android.synthetic.main.fragment_commande.*
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CmdModel: ViewModel() {


     var list:List<Commande>? = null


    fun loadData(act: Activity) {
         act.progressBar2.visibility = View.VISIBLE


        getCommandesFromRemote(act)
        act.progressBar.visibility = View.GONE


    }

    private fun getCommandesFromRemote(act:Activity) {
        val call = RetrofitService.endpoint.getCommandes()
        call.enqueue(object : Callback<List<Commande>> {
            override fun onResponse(call: Call<List<Commande>>?, response: Response<List<Commande>>?) {
                act.progressBar2.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    list = response?.body()
                    act.progressBar2.visibility = View.GONE
                    act.listcmd.adapter = CommandeAdapter(act, list!!)
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

}
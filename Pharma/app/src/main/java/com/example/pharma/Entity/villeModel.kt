package com.example.pharma.Entity

import android.app.Activity
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.pharma.R
import com.example.pharma.Retrofit.RetrofitService
import com.example.pharma.RoomDataBase.RoomService
import kotlinx.android.synthetic.main.fragment_ville.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class villeModel : ViewModel() {
    var list:ArrayList<String>? = null


    fun loadData(act: Activity)
    {
         val listville = RoomService.appDataBase.getVilleDao().getVilles()

        if (listville?.size == 0)
        {
            getVillesFromRemote(act)

        }
        else
        {
            convertToString(listville)
            val arrayAdapter = ArrayAdapter<String>(
                act,
                R.layout.support_simple_spinner_dropdown_item, list!!
            )
            act.android_material_design_spinner.adapter=arrayAdapter
        }
    }

    private fun getVillesFromRemote(act:Activity) {
        val call = RetrofitService.endpoint.getVilles()
        call.enqueue(object : Callback<List<Ville>> {
            override fun onResponse(call: Call<List<Ville>>?, response: Response<List<Ville>>?) {
                 if (response?.isSuccessful!!) {
                    convertToString(response.body()!!)

                     val arrayAdapter = ArrayAdapter<String>(
                        act,
                        R.layout.support_simple_spinner_dropdown_item, list!!
                    )
                    act.android_material_design_spinner.adapter=arrayAdapter

                    RoomService.appDataBase.getVilleDao().addVilles(response.body()!!)
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Ville>>?, t: Throwable?) {
                 act.toast("Une erreur s'est produite")
            }


        })
    }
    private fun convertToString(l:List<Ville>)
    {
        list= ArrayList(l.size)
        for( i in l)
        {
            list?.add(i.nom)
        }

    }
}
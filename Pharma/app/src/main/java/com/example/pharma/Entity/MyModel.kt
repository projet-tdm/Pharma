package com.example.pharma.Entity
import androidx.lifecycle.ViewModel
import com.example.pharma.Entity.Pharmacie

class MyModel: ViewModel() {

    val list = getData()
    private fun getData(): List<Pharmacie> {
        // val cmd=Commande(1,R.drawable.ic_launcher_background,"ok")
        val listpharma= mutableListOf<Pharmacie>()
        val ouv= mutableListOf<String>("8:00","8:00","8:00","8:00","8:00","8:00","8:00","8:00")
        val fer= mutableListOf<String>("00:00","00:00","00:00","00:00","00:00","00:00","00:00","00:00")
        val pharma= Pharmacie(
            "Pharmacie el Bahdja",
            "Dishant Hospital,sétif",
            "07 77 77 77",
            arrayListOf(),
            "ABC",
            "facebok",
            "sétif",
            ouv,
            fer
        )
        listpharma.add(pharma)
        listpharma.add(pharma)
        listpharma.add(pharma)
        listpharma.add(pharma)
        listpharma.add(pharma)
        listpharma.add(pharma)
        listpharma.add(pharma)




        return listpharma

    }
}
package com.example.pharma.RoomDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pharma.Entity.Pharmacie

@Dao
interface PharmacieDao {
    @Query("select * from pharmacies where id_ville=:ville")
    fun getPharmacies(ville:String):List<Pharmacie>
    @Insert
    fun addPharmacies(cities:List<Pharmacie>)
    @Query("select * from pharmacies where id_pharma=:id")
    fun getPharmaById(id:Int):Pharmacie
}

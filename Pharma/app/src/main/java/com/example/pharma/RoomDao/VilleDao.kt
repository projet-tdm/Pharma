package com.example.pharma.RoomDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pharma.Entity.Ville

@Dao
interface VilleDao {
    @Query("select * from villes ")
    fun getVilles():List<Ville>
    @Insert
    fun addVilles(cities:List<Ville>)
}
package com.example.pharma.RoomDataBase
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.Entity.Ville
import com.example.pharma.RoomDao.PharmacieDao
import com.example.pharma.RoomDao.VilleDao


@Database(entities = arrayOf(Pharmacie::class, Ville::class),version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getPharamcieDao(): PharmacieDao
    abstract fun getVilleDao(): VilleDao


}
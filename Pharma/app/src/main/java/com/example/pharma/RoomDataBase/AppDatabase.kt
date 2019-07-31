package com.example.pharma.RoomDataBase
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.RoomDao.PharmacieDao


@Database(entities = arrayOf(Pharmacie::class),version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getPharamcieDao(): PharmacieDao

}
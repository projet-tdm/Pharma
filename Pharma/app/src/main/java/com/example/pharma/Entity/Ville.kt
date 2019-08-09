package com.example.pharma.Entity
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "villes")
data class Ville(@PrimaryKey val id :Int, val nom:String, val localisation:String):Serializable
package com.example.pharma.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import kotlin.collections.ArrayList
@Entity(tableName = "pharmacies")
data class Pharmacie(@PrimaryKey val id_pharma:Int, val nom:String, val adresse:String, val tel:String, val cassConv:String, val facebook:String, val location:String, val horaireOv:String, val horaireFerm:String, val id_ville:Int, val garde:String):Serializable
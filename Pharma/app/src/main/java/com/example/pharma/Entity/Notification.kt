package com.example.pharma.Entity

import androidx.room.PrimaryKey
import java.io.Serializable

data class Notification (@PrimaryKey val id:Int,val commande:Int ,val user:Int,val date:String,var vue :Int,val pharmacie:String,val dateL:String):Serializable
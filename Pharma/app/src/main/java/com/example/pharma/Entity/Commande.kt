package com.example.pharma.Entity
import java.io.*
data class Commande(val numero:Int, val photo:String, var etat:String, val pharma: String, val date:String):Serializable
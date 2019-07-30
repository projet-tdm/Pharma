package com.example.pharma.Entity
import java.io.*
data class Commande(val numero:Int, val photo:Int, var etat:String, val pharma: Pharmacie, val date:String):Serializable
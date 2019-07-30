package com.example.pharma.Entity
import java.io.Serializable

data class Ville(val nom:String,val tabPharma:MutableList<Pharmacie>):Serializable
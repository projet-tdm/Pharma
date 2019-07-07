package com.example.pharma
import java.io.Serializable

data class Ville(val nom:String,val tabPharma:MutableList<Pharmacie>):Serializable
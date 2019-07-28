package com.example.pharma

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Pharmacie(val nom:String,val adresse:String,val tel:String,var tabCmd:ArrayList<Commande>,val cassConv:String,val facebook:String,val location:String,val horaireOv:List<String>,val horaireFerm:List<String>):Serializable
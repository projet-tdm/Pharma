package com.example.pharma.Entity

data class Compte (val nss:Int,val nom:String,val prenom:String,val adresse:String,val tel:String,var tabCmd:Array<Commande>)

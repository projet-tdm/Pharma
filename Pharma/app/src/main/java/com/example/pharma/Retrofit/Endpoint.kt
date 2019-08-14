package com.example.pharma.Retrofit

import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.Entity.MyResponse
import com.example.pharma.Entity.Ville
import okhttp3.ResponseBody

import retrofit2.Call

import retrofit2.http.*


interface Endpoint {
    @GET("getpharmacies/{ville}")
    fun getPharmacies(@Path("ville") ville_id:String): Call<ArrayList<Pharmacie>>
    @GET("getvilles")
    fun getVilles(): Call<List<Ville>>
    @GET("getPharma")
    fun getPharma(): Call<List<Pharmacie>>
    @GET("getcommandes/{nss}")
    fun getCommandes(@Path("nss") user:Int): Call<List<Commande>>
    @FormUrlEncoded
    @POST("upload1.php")
    fun addCmd(@Field("photo") photo:String, @Field("etat") etat: String, @Field("pharma") pharma:String, @Field("date") date: String,@Field("user") user: Int,@Field("name") name:String): Call<MyResponse>
    @PUT("annuler/{id}")
    fun annulerCmd(@Path("id") id:Int):Call<ResponseBody>
    @GET("getpharmaciesGarde/")
    fun getPharmaciesGarde(): Call<ArrayList<Pharmacie>>
}
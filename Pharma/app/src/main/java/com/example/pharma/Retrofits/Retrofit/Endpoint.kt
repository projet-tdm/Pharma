package com.example.pharma.Retrofits.Retrofit

import com.example.pharma.Entity.*
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
    @PUT("payer/{id}")
    fun payerCmd(@Path("id") id:Int):Call<ResponseBody>
    @GET("getpharmaciesGarde/")
    fun getPharmaciesGarde(): Call<ArrayList<Pharmacie>>
    @GET("getNotifications/{user}")
    fun getNotif(@Path("user") user:Int): Call<List<Notification>>
    @GET("getCommande/{numero}")
    fun getCommande(@Path("numero") numero:Int): Call<List<Commande>>
    @PUT("setVue/{id}")
    fun setVue(@Path("id") id:Int):Call<ResponseBody>
}
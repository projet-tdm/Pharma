package com.example.pharma.Retrofit

import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.Entity.MyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.http.*


interface Endpoint {
    @GET("getpharmacies")
    fun getPharmacies(): Call<List<Pharmacie>>
    @GET("getcommandes")
    fun getCommandes(): Call<List<Commande>>
    @FormUrlEncoded
    @POST("upload1.php")
    fun addCmd(@Field("photo") photo:String, @Field("etat") etat: String, @Field("pharma") pharma:String, @Field("date") date: String): Call<MyResponse>
}
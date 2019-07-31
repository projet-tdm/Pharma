package com.example.pharma.Retrofit

import com.example.pharma.Entity.Pharmacie
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @GET("getpharmacies")
    fun getPharmacies(): Call<List<Pharmacie>>
}
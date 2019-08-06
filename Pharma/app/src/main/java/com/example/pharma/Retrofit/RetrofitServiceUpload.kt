package com.example.pharma.Retrofit

import com.example.pharma.Entity.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceUpload {
    val endpoint : Endpoint by lazy {
        Retrofit.Builder().baseUrl(baseUrl). addConverterFactory(GsonConverterFactory.create()). build().create(
            Endpoint::class.java)
    }
}
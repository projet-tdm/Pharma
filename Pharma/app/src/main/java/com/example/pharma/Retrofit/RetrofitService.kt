package com.example.pharma.Retrofit

import com.example.pharma.Entity.baseUrl
import com.example.pharma.Entity.baseUrl1
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val endpoint : Endpoint by lazy {
        Retrofit.Builder().baseUrl(baseUrl1). addConverterFactory(GsonConverterFactory.create()). build().create(
            Endpoint::class.java)
    }
    val endpointUpload : Endpoint by lazy {
        Retrofit.Builder().baseUrl(baseUrl). addConverterFactory(GsonConverterFactory.create()). build().create(
            Endpoint::class.java)
    }
}
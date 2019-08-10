package com.example.pharma.UsersRetrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val endpoint : Endpoint by lazy {
        Retrofit.Builder().baseUrl("https://300fba13.ngrok.io").
            addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }
    val usersEndpoint : UsersEndpoint by lazy {
        Retrofit.Builder().baseUrl("https://b396ac03.ngrok.io").
            addConverterFactory(GsonConverterFactory.create()).
            build().create(UsersEndpoint::class.java)
    }

}
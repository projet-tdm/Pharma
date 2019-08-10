package com.example.pharma

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val endpoint :Endpoint by lazy {
        Retrofit.Builder().baseUrl("http://192.168.1.6:5000/").
            addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }
    val usersEndpoint :UsersEndpoint by lazy {
        Retrofit.Builder().baseUrl("http://192.168.1.6:8082/").
            addConverterFactory(GsonConverterFactory.create()).
            build().create(UsersEndpoint::class.java)
    }

}
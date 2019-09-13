package com.example.pharma.UsersRetrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val endpoint : Endpoint by lazy {

        Retrofit.Builder().baseUrl("http://192.168.43.68:5000").
            addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }

    val usersEndpoint : UsersEndpoint by lazy {
        Retrofit.Builder().baseUrl("http://192.168.43.128:8082").

            addConverterFactory(GsonConverterFactory.create()).
            build().create(UsersEndpoint::class.java)
    }

}
package com.example.pharma.Retrofits.UsersRetrofit


import com.example.pharma.Entity.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val endpoint : Endpoint by lazy {
        Retrofit.Builder().baseUrl(twilioURL).
            addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }

    val usersEndpoint : UsersEndpoint by lazy {

        Retrofit.Builder().baseUrl(usersURL).
             addConverterFactory(GsonConverterFactory.create()).
            build().create(UsersEndpoint::class.java)
    }

}
package com.example.pharma.PaymentRetrofit

import com.example.pharma.UsersRetrofit.UsersEndpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PaymentRetrofit {
    val paymentendpoint : PaymentEndpoint by lazy {
        Retrofit.Builder().baseUrl("http://192.168.2.35:5000").
            addConverterFactory(GsonConverterFactory.create()).build().create(PaymentEndpoint::class.java)
    }
}
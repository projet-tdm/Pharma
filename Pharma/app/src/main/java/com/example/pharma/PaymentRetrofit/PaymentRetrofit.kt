package com.example.pharma.PaymentRetrofit

import com.example.pharma.UsersRetrofit.UsersEndpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PaymentRetrofit {
    val paymentendpoint : PaymentEndpoint by lazy {
        Retrofit.Builder().baseUrl(" http://2b199a03.ngrok.io").
            addConverterFactory(GsonConverterFactory.create()).build().create(PaymentEndpoint::class.java)
    }
}
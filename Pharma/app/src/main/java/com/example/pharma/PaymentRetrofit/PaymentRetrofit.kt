package com.example.pharma.PaymentRetrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PaymentRetrofit {
    val paymentendpoint : PaymentEndpoint by lazy {

        Retrofit.Builder().baseUrl("http://36a24ade.ngrok.io").

            addConverterFactory(GsonConverterFactory.create()).build().create(PaymentEndpoint::class.java)
    }
}
package com.example.pharma.Retrofits.PaymentRetrofit

import retrofit2.Call
import retrofit2.http.GET

interface PaymentEndpoint {
    @GET("getToken/")
    fun getToken(): Call<String>
}
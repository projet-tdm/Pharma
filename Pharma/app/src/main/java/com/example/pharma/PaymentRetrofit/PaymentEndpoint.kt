package com.example.pharma.PaymentRetrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PaymentEndpoint {
    @GET("getToken/")
    fun getToken(): Call<String>
}
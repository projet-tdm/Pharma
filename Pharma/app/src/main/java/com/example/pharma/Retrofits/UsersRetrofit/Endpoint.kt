package com.example.pharma.Retrofits.UsersRetrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("sms/{passwd}/{tel}")
    fun sendSms(@Path("passwd") passwd:String,@Path("tel") tel:Int): Call<String>
}
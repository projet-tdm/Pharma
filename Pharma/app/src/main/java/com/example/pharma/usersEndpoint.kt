package com.example.pharma

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersEndpoint {
    @POST("addUser/")
    fun addUser(@Body user:User): Call<String>

    @POST("alterUserPass/")
    fun alterUserPass(@Body renewObject: RenewObject): Call<String>

    @GET("getUserByTel/{tel}")
    fun getUserByTel(@Path("tel") tel:Int):Call<List<User>>
}
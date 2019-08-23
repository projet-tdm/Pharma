package com.example.pharma.UsersRetrofit

import com.example.pharma.Entity.Device
import com.example.pharma.RenewObject
import com.example.pharma.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersEndpoint {
    @POST("addUser/")
    fun addUser(@Body user: User): Call<String>
    @POST("addDevice/")
    fun addDevice(@Body device: Device): Call<String>

    @POST("alterUserPass/")
    fun alterUserPass(@Body renewObject: RenewObject): Call<String>

    @GET("getUserByTel/{tel}")
    fun getUserByTel(@Path("tel") tel:Int):Call<List<User>>
}
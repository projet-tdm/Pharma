package com.example.pharma.Entity
/**
 * Created by Android on 2/17/2018.
 */
import com.google.gson.annotations.SerializedName

class MyResponse {

    @SerializedName("photo")
    private val photo: String? = null
    @SerializedName("etat")
    private val etat: String? = null
    @SerializedName("pharma")
    private val pharma: Int? =0
    @SerializedName("date")
    private val date: String? = null
    @SerializedName("response")
    val response: String? = null
}
package com.example.retrofitkotlin.services

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {

    @GET("common/get_products.php")
    fun getData(): Call<JsonObject>

    @POST("common/get_product.php")
    fun getDatas(@QueryMap filter: HashMap<String,String>): Call<JsonObject>
}
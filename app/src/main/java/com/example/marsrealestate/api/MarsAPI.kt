package com.example.marsrealestate.api

import com.example.marsrealestate.data.MarsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsAPI {
    @GET("realestate")
    suspend fun getProperties(@Query("filter") filterType: String): Response<List<MarsData>>
}
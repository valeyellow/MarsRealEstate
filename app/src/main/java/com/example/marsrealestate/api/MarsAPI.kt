package com.example.marsrealestate.api

import com.example.marsrealestate.data.MarsData
import retrofit2.Response
import retrofit2.http.GET

interface MarsAPI {
    @GET("realestate")
    suspend fun getProperties(): Response<List<MarsData>>
}
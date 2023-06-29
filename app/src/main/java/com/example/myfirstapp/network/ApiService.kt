package com.example.myfirstapp.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("daily_json.js")
    suspend fun getCurrencyCourse(): Response<CurrencyList>

}
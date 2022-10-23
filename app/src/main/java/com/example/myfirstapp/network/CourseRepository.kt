package com.example.myfirstapp.network

import android.content.ClipData.Item
import retrofit2.Response


class CourseRepository {

    suspend fun getCurrency() : Response<CurrencyList> {
        val itemService: ApiService = RetrofitInstance
            .getRetrofitInstance(CurrencyList::class.java, CurrencyDeserializer())!!
            .create(ApiService::class.java)
        return itemService.getCurrencyCourse()
    }
}
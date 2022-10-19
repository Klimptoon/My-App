package com.example.myfirstapp.network

import retrofit2.Response

class CourseRepository {
    suspend fun getCurrency() : Response<Currency> {
        return RetrofitInstance.api.getCurrencyCourse()
    }
}
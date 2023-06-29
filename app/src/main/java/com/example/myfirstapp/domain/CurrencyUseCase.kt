package com.example.myfirstapp.domain

import com.example.myfirstapp.network.CourseRepository
import com.example.myfirstapp.network.CurrencyList
import retrofit2.Response

class CurrencyUseCase() {

    private val repository = CourseRepository()

    suspend fun getCurrency(): Response<CurrencyList> {
        return repository.getCurrency()
    }

}
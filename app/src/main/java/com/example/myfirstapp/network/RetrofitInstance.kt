package com.example.myfirstapp.network

import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class RetrofitInstance {


    companion object {
        private fun createGsonConverter(type: Type, typeAdapter: Any): Converter.Factory? {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(type, typeAdapter)
            val gson = gsonBuilder.create()

            return GsonConverterFactory.create(gson)
        }


        fun getRetrofitInstance(type: Type, typeAdapter: Any): Retrofit? {
            return Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(createGsonConverter(type, typeAdapter))
                .build()
        }
    }






}
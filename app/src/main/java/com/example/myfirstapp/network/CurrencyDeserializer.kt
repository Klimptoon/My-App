//package com.example.myfirstapp.network
//
//import com.google.gson.JsonDeserializationContext
//import com.google.gson.JsonDeserializer
//import com.google.gson.JsonElement
//import com.google.gson.JsonObject
//import java.lang.reflect.Type
//
//class CurrencyDeserializer : JsonDeserializer<Currency> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): Currency {
//        val jsonObject : JsonObject = json?.asJsonObject ?: throw NullPointerException()
//
//    }
//}
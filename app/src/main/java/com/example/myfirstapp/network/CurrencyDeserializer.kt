package com.example.myfirstapp.network

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.http.GET
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<List<Currency>> {

    val charCodeNames = listOf("AUD", "AZN", "GBP", "AMD", "BYN", "BGN", "BRL", "HUF", "HKD", "DKK", "USD", "EUR",
        "INR", "KZT", "CAD", "KGS", "CNY", "MDL", "NOK", "PLN", "RON", "XDR", "SGD", "TJS", "TRY", "TMT", "UZS", "UAH", "CZK",
        "SEK", "CHF", "ZAR", "KRW", "JPY")
    var currencyList = ArrayList<Currency>()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Currency> {
        val jsonObject : JsonObject = json?.asJsonObject ?: throw NullPointerException()


        
        for ((index, charCodeName) in charCodeNames.withIndex()) {
            jsonObject.getAsJsonObject(charCodeNames[index])
            val charCode = jsonObject.get("CharCode").asString
            val nominal = jsonObject.get("Nominal").asInt
            val name = jsonObject.get("Name").asString
            val value = jsonObject.get("Value").asDouble
            currencyList.add(Currency(charCode, nominal, name, value))
        }

        return currencyList
    }
}
package com.example.myfirstapp.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<CurrencyList> {

    val charCodeNames = listOf("USD", "EUR", "BYN", "UAH", "AUD", "AZN", "AMD", "BGN", "BRL", "HUF", "HKD", "DKK",
        "INR", "KZT", "CAD", "KGS", "CNY", "MDL", "NOK", "PLN", "RON", "SGD", "TJS", "TRY", "UZS", "CZK",
        "SEK", "CHF", "ZAR", "KRW", "JPY")

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyList {
        val jsonObject: JsonObject = json?.asJsonObject ?: throw NullPointerException()

        val list = mutableListOf<Currency>()

        charCodeNames.forEach {
            val valute = jsonObject.getAsJsonObject("Valute").getAsJsonObject(it)
            val charCode = valute.get("CharCode").asString
            val nominal = valute.get("Nominal").asInt
            val name = valute.get("Name").asString
            val value = valute.get("Value").asDouble
            list.add(Currency(charCode, nominal, name, value))
        }
        return CurrencyList(list)
    }
}
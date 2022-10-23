package com.example.myfirstapp.network

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("CharCode")
    val charCode : String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("Name")
    val name : String,
    @SerializedName("Value")
    val value : Double,
)

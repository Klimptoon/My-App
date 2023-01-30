package com.example.myfirstapp.presentation

import kotlinx.android.parcel.Parcelize

data class Purchase(val id: Int = 0, val imageId: Int, val type: String, val title: String, val cost: String, val date: String = "")


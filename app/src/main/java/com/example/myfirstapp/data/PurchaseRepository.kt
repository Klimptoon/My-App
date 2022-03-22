package com.example.myfirstapp.data

import com.example.myfirstapp.R
import com.example.myfirstapp.presentation.Purchase

class PurchaseRepository {
    fun getData(): List<Purchase> {
        val purchase1 = Purchase(R.drawable.circle_shape_red, "Еда", "Помидоры", "1230")
        val purchase2 = Purchase(R.drawable.circle_shape_blue, "Одежда", "Штаны", "1500")
        val purchase3 = Purchase(R.drawable.circle_shape_red, "Дом", "Шторы", "5000")
        return listOf(purchase1, purchase2, purchase3)
    }
}
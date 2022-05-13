package com.example.myfirstapp.data

import com.example.myfirstapp.R
import com.example.myfirstapp.presentation.Purchase

class PurchaseRepository {
    fun getData(): List<Purchase> {
        val purchase1 = Purchase(R.drawable.circle_shape_red, "Еда", "Помидоры", "1230", "13.05.2022")
        val purchase2 = Purchase(R.drawable.circle_shape_red, "Еда", "Огурцы", "2000", "11.03.2022")
        val purchase3 = Purchase(R.drawable.circle_shape_red, "Еда", "Шоколад", "3000", "12.05.2022")
        val purchase4 = Purchase(R.drawable.circle_shape_blue, "Одежда", "Кофта", "3500", "07.05.2022")
        val purchase5 = Purchase(R.drawable.circle_shape_blue, "Одежда", "Штаны", "4500", "08.01.2022")
        val purchase6 = Purchase(R.drawable.circle_shape_blue, "Одежда", "Перчатки", "5500", "11.05.2022")
        val purchase7 = Purchase(R.drawable.circle_shape_green, "Дом", "Шторы", "5000", "01.05.2022")
        val purchase8 = Purchase(R.drawable.circle_shape_green, "Дом", "Стол", "5000", "01.05.2022")
        val purchase9 = Purchase(R.drawable.circle_shape_green, "Дом", "Табуретка", "5000", "01.03.2012")
        return listOf(purchase1, purchase2, purchase3, purchase4,purchase5, purchase6, purchase7, purchase8, purchase9)
    }
}
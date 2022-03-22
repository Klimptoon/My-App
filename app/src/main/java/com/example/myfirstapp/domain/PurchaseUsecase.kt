package com.example.myfirstapp.domain

import com.example.myfirstapp.data.PurchaseRepository
import com.example.myfirstapp.presentation.Purchase

class PurchaseUsecase {
    val purchaseRepository = PurchaseRepository()

    fun getData() : List<Purchase> {
        return purchaseRepository.getData()
    }

}
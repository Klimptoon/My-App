package com.example.myfirstapp.domain

import com.example.myfirstapp.data.PurchaseRepository
import com.example.myfirstapp.presentation.Purchase

class PurchaseUsecase {
    val purchaseRepository = PurchaseRepository()

    fun getData(type : String) : List<Purchase> {
        val purchaseList = purchaseRepository.getData()
        val listOfType = mutableListOf<Purchase>()
        for (purchase in purchaseList) {
            if(purchase.type == type) {
                listOfType.add(purchase)
            }
        }
        return listOfType
    }
    fun getStartData() : List<Purchase> {
        return purchaseRepository.getData()
    }



}
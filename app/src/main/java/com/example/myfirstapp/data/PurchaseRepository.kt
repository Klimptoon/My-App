package com.example.myfirstapp.data

import com.example.myfirstapp.R
import com.example.myfirstapp.presentation.Purchase

class PurchaseRepository(private val purchaseDao : PurchaseDao) {


    suspend fun addPurchase(purchase : Purchase) {
        val entity = PurchaseEnt.fromPurchase(purchase)
        purchaseDao.addPurchase(entity)
    }
    suspend fun getData() : List<Purchase> {
        return purchaseDao.getData().map{ PurchaseEnt -> PurchaseEnt.toPurchase() }
    }

}
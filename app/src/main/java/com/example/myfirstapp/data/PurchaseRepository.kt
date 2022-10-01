package com.example.myfirstapp.data

import com.example.myfirstapp.R
import com.example.myfirstapp.presentation.Purchase

class PurchaseRepository(private val purchaseDao : PurchaseDao) {


    fun addPurchase(purchase : Purchase) {
        val entity = PurchaseEnt.fromPurchase(purchase)
        purchaseDao.addPurchase(entity)
    }
    fun getData() : List<Purchase> {
        return purchaseDao.getData().map{ PurchaseEnt -> PurchaseEnt.toPurchase() }
    }

}
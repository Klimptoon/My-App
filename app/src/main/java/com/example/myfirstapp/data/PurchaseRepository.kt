package com.example.myfirstapp.data

import android.util.Log
import com.example.myfirstapp.presentation.Purchase

class PurchaseRepository(private val purchaseDao: PurchaseDao) {

    suspend fun addPurchase(purchase: Purchase) {
        val entity = PurchaseEnt.fromPurchase(purchase)
        purchaseDao.addPurchase(entity)
    }

    suspend fun getData(): List<Purchase> {
        return purchaseDao.getData().map { PurchaseEnt -> PurchaseEnt.toPurchase() }
    }

    suspend fun deletePurchase(purchase: Purchase) {
        val entity = PurchaseEnt.fromPurchase(purchase)
        Log.d("ff", purchaseDao.deletePurchase(entity).toString())
    }

}
package com.example.myfirstapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myfirstapp.presentation.Purchase


@Dao
interface PurchaseDao {

    @Insert
    fun addPurchase(purchase : PurchaseEnt)

    @Query("SELECT * FROM purchase_table")
    fun getData() : List<PurchaseEnt>

    @Delete
    fun deletePurchase(purchase: PurchaseEnt)
}
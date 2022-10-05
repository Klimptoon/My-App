package com.example.myfirstapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myfirstapp.presentation.Purchase


@Dao
interface PurchaseDao {

    @Insert
    suspend fun addPurchase(purchase : PurchaseEnt)

    @Query("SELECT * FROM purchase_table")
    suspend fun getData() : List<PurchaseEnt>

    @Delete
    suspend fun deletePurchase(purchase: PurchaseEnt)
}
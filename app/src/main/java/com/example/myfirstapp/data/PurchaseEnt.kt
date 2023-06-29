package com.example.myfirstapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfirstapp.presentation.Purchase

@Entity(tableName = "purchase_table")
data class PurchaseEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "image_id") val imageId: Int,
    val type: String,
    val title: String,
    val cost: String,
    val date: String
) {
    fun toPurchase(): Purchase = Purchase(
        id = id,
        imageId = imageId,
        type = type,
        title = title,
        cost = cost,
        date = date
    )

    companion object {
        fun fromPurchase(purchase: Purchase): PurchaseEnt = PurchaseEnt(
            id = purchase.id,
            purchase.imageId,
            type = purchase.type,
            title = purchase.title,
            cost = purchase.cost,
            date = purchase.date
        )
    }
}


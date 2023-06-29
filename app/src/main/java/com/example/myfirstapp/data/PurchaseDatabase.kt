package com.example.myfirstapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PurchaseEnt::class], version = 1, exportSchema = false)
abstract class PurchaseDatabase : RoomDatabase() {

    abstract fun getPurchaseDao(): PurchaseDao

    companion object {
        private var database: PurchaseDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PurchaseDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(context, PurchaseDatabase::class.java, "db")
                    .build()
                database as PurchaseDatabase
            } else {
                database as PurchaseDatabase
            }
        }
    }

}
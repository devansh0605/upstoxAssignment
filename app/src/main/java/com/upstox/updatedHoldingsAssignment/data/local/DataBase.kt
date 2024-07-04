package com.upstox.updatedHoldingsAssignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HoldingEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}
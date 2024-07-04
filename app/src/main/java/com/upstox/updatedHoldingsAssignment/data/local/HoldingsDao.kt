package com.upstox.updatedHoldingsAssignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolding(persons: List<HoldingEntity>)

    @Query("SELECT * FROM holdingentity")
    fun getAllHoldings(): Flow<List<HoldingEntity>>

    @Query("DELETE FROM holdingentity")
    suspend fun deleteAllHoldings()
}
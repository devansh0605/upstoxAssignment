package com.upstox.updatedHoldingsAssignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HoldingEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null
)
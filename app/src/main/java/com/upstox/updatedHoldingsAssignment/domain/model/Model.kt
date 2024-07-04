package com.upstox.updatedHoldingsAssignment.domain.model

data class Holding(
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null
)
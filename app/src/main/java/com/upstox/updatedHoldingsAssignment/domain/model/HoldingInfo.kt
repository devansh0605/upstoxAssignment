package com.upstox.updatedHoldingsAssignment.domain.model

/**
 *  DTO to hold data that needs to be displayed on the UI.
 */
data class HoldingInfo(
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null,
    val netProfitOrLoss: Double? = null
)

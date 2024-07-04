package com.upstox.updatedHoldingsAssignment.data.mapper

import com.upstox.updatedHoldingsAssignment.data.local.HoldingEntity
import com.upstox.updatedHoldingsAssignment.domain.model.Holding
import com.upstox.updatedHoldingsAssignment.domain.model.HoldingInfo
import com.upstox.updatedHoldingsAssignment.utilities.calculateProfitAndLoss

/**
 *  To convert HoldingEntity to HoldingInfo DTO.
 */
fun HoldingEntity.toHoldingInfo(): HoldingInfo {
    return HoldingInfo(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close,
        netProfitOrLoss = calculateProfitAndLoss(this)
    )
}

/**
 *  To convert Holding to HoldingEntity DTO.
 */
fun Holding.toHoldingEntity(): HoldingEntity {
    return HoldingEntity(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}
package com.upstox.updatedHoldingsAssignment.data.remote

import com.upstox.updatedHoldingsAssignment.domain.model.Holding

/**
 *  Response format of getAllHoldings API.
 */
data class GetAllHoldingsResponse(val data: Data)

data class Data(val userHolding: List<Holding>)
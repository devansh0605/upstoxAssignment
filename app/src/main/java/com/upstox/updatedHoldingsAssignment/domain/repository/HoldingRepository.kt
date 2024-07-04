package com.upstox.updatedHoldingsAssignment.domain.repository

import com.upstox.updatedHoldingsAssignment.data.local.HoldingEntity
import com.upstox.updatedHoldingsAssignment.domain.model.HoldingInfo
import com.upstox.updatedHoldingsAssignment.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface HoldingRepository {
    suspend fun getHoldingDataFromRemote(): Flow<Resource<List<HoldingInfo>>>

    fun getHoldingDataFromLocal(): Flow<List<HoldingInfo>>

    suspend fun insertHoldingData(holdings: List<HoldingEntity>)
}
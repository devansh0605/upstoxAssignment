package com.upstox.updatedHoldingsAssignment.data.repository

import com.upstox.updatedHoldingsAssignment.data.local.HoldingEntity
import com.upstox.updatedHoldingsAssignment.data.local.HoldingsDao
import com.upstox.updatedHoldingsAssignment.data.mapper.toHoldingEntity
import com.upstox.updatedHoldingsAssignment.data.mapper.toHoldingInfo
import com.upstox.updatedHoldingsAssignment.data.remote.HoldingService
import com.upstox.updatedHoldingsAssignment.domain.model.HoldingInfo
import com.upstox.updatedHoldingsAssignment.domain.repository.HoldingRepository
import com.upstox.updatedHoldingsAssignment.utilities.NoConnectivityException
import com.upstox.updatedHoldingsAssignment.utilities.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HoldingRepositoryImpl @Inject constructor(
    private val holdingsDao: HoldingsDao,
    private val holdingService: HoldingService
) : HoldingRepository {

    /**
     *  To fetch holdings data from remote API.
     */
    override suspend fun getHoldingDataFromRemote(): Flow<Resource<List<HoldingInfo>>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteData = try {
                val response = holdingService.getAllHoldings()
                emit(Resource.Success())
                response.body()?.data?.userHolding?.map { it.toHoldingEntity() }
            } catch (e: NoConnectivityException) {
                emit(Resource.Error(e.message))
                null
            } catch (e: Exception) {
                emit(Resource.Error("Couldn't load data"))
                null
            }

            /**
             *  If data fetched from server is non-null, then it will be stored in local DB.
             */
            remoteData?.let {
                holdingsDao.deleteAllHoldings()
                insertHoldingData(it)
            }

            emit(Resource.Loading(false))
        }
    }

    /**
     *  To get holding list from local DB as a flow.
     */
    override fun getHoldingDataFromLocal(): Flow<List<HoldingInfo>> {
        return flow {
            holdingsDao.getAllHoldings().collect { holdingList ->
                emit(holdingList.map { it.toHoldingInfo() })
            }
        }
    }

    /**
     *  To insert new records in holding list.
     */
    override suspend fun insertHoldingData(holdings: List<HoldingEntity>) =
        withContext(Dispatchers.IO) {
            holdingsDao.insertHolding(holdings)
        }
}
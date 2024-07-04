package com.upstox.updatedHoldingsAssignment.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface HoldingService {

    @GET("/")
    suspend fun getAllHoldings(): Response<GetAllHoldingsResponse>
}
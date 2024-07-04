package com.upstox.updatedHoldingsAssignment.di

import com.upstox.updatedHoldingsAssignment.data.repository.HoldingRepositoryImpl
import com.upstox.updatedHoldingsAssignment.domain.repository.HoldingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHoldingRepository(
        holdingRepositoryImpl: HoldingRepositoryImpl
    ): HoldingRepository
}
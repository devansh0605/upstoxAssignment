package com.upstox.updatedHoldingsAssignment.di

import androidx.room.Room
import com.upstox.updatedHoldingsAssignment.AppClass
import com.upstox.updatedHoldingsAssignment.data.local.DataBase
import com.upstox.updatedHoldingsAssignment.data.local.HoldingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    companion object {
        const val DATABASE_NAME = "holding_database"
    }

    @Singleton
    @Provides
    fun provideDatabase(): DataBase {
        return Room
            .databaseBuilder(
                AppClass.getContext(),
                DataBase::class.java,
                DATABASE_NAME
            ).build()
    }

    @Singleton
    @Provides
    fun provideHoldingDao(database: DataBase): HoldingsDao {
        return database.holdingsDao()
    }
}
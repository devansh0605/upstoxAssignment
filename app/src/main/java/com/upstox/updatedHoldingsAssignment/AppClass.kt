package com.upstox.updatedHoldingsAssignment

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upstox.updatedHoldingsAssignment.utilities.ConnectionState
import com.upstox.updatedHoldingsAssignment.utilities.getCurrentConnectivityState
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClass : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: AppClass? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }

        /**
         *  Live data to monitor network connectivity state.
         */
        val connectivityState: MutableLiveData<ConnectionState> by lazy {
            MutableLiveData<ConnectionState>(getCurrentConnectivityState())
        }
    }
}
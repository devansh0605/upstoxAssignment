package com.upstox.updatedHoldingsAssignment.utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.upstox.updatedHoldingsAssignment.AppClass

const val CONNECTIVITY_INTENT_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

/**
 *  Broadcast receiver of network connectivity.
 */
val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == CONNECTIVITY_INTENT_ACTION) {
            getCurrentConnectivityState().let {
                if (it != AppClass.connectivityState.value) {
                    AppClass.connectivityState.value = it
                }
            }
        }
    }
}

/**
 *  To get current connectivity state.
 */
fun getCurrentConnectivityState(): ConnectionState {
    val connectivityManager =
        AppClass.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return ConnectionState.Unavailable

    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        ?: return ConnectionState.Unavailable

    return when {
        (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) &&
                internetConnected(networkCapabilities) -> {
            ConnectionState.Available
        }

        else -> {
            ConnectionState.Unavailable
        }
    }
}

/**
 *  To verify if active network has internet access or not.
 */
fun internetConnected(networkCapabilities: NetworkCapabilities): Boolean {
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}
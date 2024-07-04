package com.upstox.updatedHoldingsAssignment.utilities

import com.upstox.updatedHoldingsAssignment.AppClass
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 *  To verify if network is connected or not while calling an API.
 */
class NetworkConnectionInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (AppClass.connectivityState.value == ConnectionState.Unavailable) {
            throw NoConnectivityException()
        }

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectivityException : IOException() {
    override val message = "No Internet Connection"
}
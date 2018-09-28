package com.vodolazskiy.twitterclient.core.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

interface NetworkStatus {

    val isConnected: Boolean
}

class NetworkStatusImpl @Inject constructor(private val context: Context) : NetworkStatus {

    override val isConnected: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.allNetworks
                    .asSequence()
                    .filterNotNull()
                    .map { connectivityManager.getNetworkInfo(it) }
                    .firstOrNull { it?.isConnected ?: false } != null
        }
}

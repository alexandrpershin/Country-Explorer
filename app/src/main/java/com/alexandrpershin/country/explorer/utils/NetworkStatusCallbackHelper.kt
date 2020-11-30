package com.alexandrpershin.country.explorer.utils

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log

@SuppressLint("NewApi")
class NetworkStatusCallbackHelper {

    private val TAG = NetworkStatusHelper::class.java.simpleName

    private var onInternetAvailableCallback: (() -> Unit)? = null

    var isOnline = false
        private set

    @Volatile
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun setCallbackOnInternetAvailable(action: () -> Unit) {
        onInternetAvailableCallback = action
    }

    fun removeCallbackOnInternetAvailable() {
        onInternetAvailableCallback = null
    }

    fun initNetworkCallback(connectivityManager: ConnectivityManager) {
        networkCallback ?: synchronized(this) {
            networkCallback ?: object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    onInternetAvailableCallback?.invoke()
                    isOnline = true
                }

                override fun onUnavailable() {
                    super.onUnavailable()

                    isOnline = false
                }
            }.also {
                networkCallback = it
                connectivityManager.registerDefaultNetworkCallback(it)
            }
        }
    }

    fun unregisterNetworkCallback(connectivityManager: ConnectivityManager) {
        networkCallback?.let {
            try {
                connectivityManager.unregisterNetworkCallback(it)
            } catch (ex: Exception) {
                Log.e(TAG, ex.message.toString())
            }

        }
    }

}
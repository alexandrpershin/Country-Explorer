package com.alexandrpershin.country.explorer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

class NetworkStatusHelper(private val context: Context) {

    private val networkStatusCallbackHelper = NetworkStatusCallbackHelper()

    fun setCallbackOnInternetAvailable(action: () -> Unit) {
        networkStatusCallbackHelper.setCallbackOnInternetAvailable(action)
    }

    fun removeCallbackOnInternetAvailable() {
        networkStatusCallbackHelper.removeCallbackOnInternetAvailable()
    }


    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isOnline: Boolean = false
        private set
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                networkStatusCallbackHelper.isOnline
            } else {
                val netInfo = connectivityManager.activeNetworkInfo
                netInfo != null && netInfo.isConnected
            }
        }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkStatusCallbackHelper.initNetworkCallback(connectivityManager)
        }
    }

    fun unregisterNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkStatusCallbackHelper.unregisterNetworkCallback(connectivityManager)
        }
    }

}


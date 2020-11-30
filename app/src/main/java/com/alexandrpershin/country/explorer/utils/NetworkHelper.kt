package com.alexandrpershin.country.explorer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

class NetworkHelper(private val context: Context) {

    private val networkStatusCallbackHelper = NetworkStatusCallbackHelper()

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

    /**
     * Trigger action once internet available again
     * */

    fun setCallbackOnInternetAvailable(action: () -> Unit) {
        networkStatusCallbackHelper.setCallbackOnInternetAvailable(action)
    }

    fun removeCallbackOnInternetAvailable() {
        networkStatusCallbackHelper.removeCallbackOnInternetAvailable()
    }

    fun unregisterNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkStatusCallbackHelper.unregisterNetworkCallback(connectivityManager)
        }
    }

}


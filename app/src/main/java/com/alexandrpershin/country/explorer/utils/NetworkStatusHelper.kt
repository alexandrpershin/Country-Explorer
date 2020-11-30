package com.alexandrpershin.country.explorer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log

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
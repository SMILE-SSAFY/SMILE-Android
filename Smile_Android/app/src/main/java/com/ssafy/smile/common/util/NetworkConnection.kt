package com.ssafy.smile.common.util

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData

private const val TAG = "스마일"
class NetworkConnection(private val context: Context): LiveData<Boolean>() {
    private var cManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var nCallback: ConnectivityManager.NetworkCallback
    private val buildSdk = Build.VERSION.SDK_INT

    override fun onActive() {
        super.onActive()
        postValue(cManager.activeNetworkInfo?.isConnected == true)

        when {
            buildSdk >= Build.VERSION_CODES.N -> cManager.registerDefaultNetworkCallback(cmCallback())
            buildSdk >= Build.VERSION_CODES.LOLLIPOP -> lollipopNetworkRequest()
            else -> context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    override fun onInactive() {
        super.onInactive()

        if (buildSdk >= Build.VERSION_CODES.LOLLIPOP) {
            cManager.unregisterNetworkCallback(cmCallback())
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest() {
        cManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                .build(),
            cmCallback()
        )
    }

    private fun cmCallback(): ConnectivityManager.NetworkCallback {
        if (buildSdk >= Build.VERSION_CODES.LOLLIPOP) {
            nCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d(TAG, "onLost: no")
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return nCallback
        } else {
            throw IllegalAccessError("Error")
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            postValue(cManager.activeNetworkInfo?.isConnected == true)
        }

    }
}
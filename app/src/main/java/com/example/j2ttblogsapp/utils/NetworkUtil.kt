package com.example.j2ttblogsapp.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtil {

    private lateinit var application: Application
    private lateinit var connectivityManager: ConnectivityManager

    fun init(application: Application) {
        this.application = application
        connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isInternetAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            if (network != null) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                return networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            return false
        } else {
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

}
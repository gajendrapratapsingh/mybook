package com.info.mybook.utils


import android.net.NetworkCapabilities
import android.os.Build
import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            // For other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            // for VPN networks
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }
    }
}

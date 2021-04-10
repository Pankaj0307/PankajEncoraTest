package com.pankaj.encora.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

object Utils {
    const val BASE_URL = "http://ax.itunes.apple.com/"
    const val DBNAME = "ENCORADB"
    const val SONG_LIMIT = 20

    /*check internet connectivity*/
    @Suppress("DEPRECATION")
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return connectivityManager.activeNetwork != null
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}
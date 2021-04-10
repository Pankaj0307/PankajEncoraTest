package com.pankaj.encora.network

import android.content.Context
import com.pankaj.encora.network.NetworkException
import com.pankaj.encora.utils.Utils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/*to check the network availability*/
class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!Utils.isConnected(context)) {
            throw NetworkException()
        }
        return chain.proceed(request)
    }
}
package com.pankaj.encora.network.api

import com.pankaj.encora.MainApplication
import com.pankaj.encora.network.NetworkConnectionInterceptor
import com.pankaj.encora.utils.Utils.BASE_URL
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * singleton class for retrofit lib and define the const require in app
 * */
object RetrofitInstance {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(NetworkConnectionInterceptor(MainApplication.applicationContext()))
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .addTypeConverter(String.javaClass, HtmlEscapeStringConverter())
                    .build()
            )
        )
        .build()

    val songsService: SongsService
        get() {
            return retrofit.create(SongsService::class.java)
        }
}

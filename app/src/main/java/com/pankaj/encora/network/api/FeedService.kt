package com.pankaj.encora.network.api

import com.pankaj.encora.network.Feed
import retrofit2.http.GET
import retrofit2.http.Path

/**service interface for API
 * */
public interface SongsService {
    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit={limit}/xml")
    suspend fun getTopSongs(@Path("limit") limit: Int): Feed
}
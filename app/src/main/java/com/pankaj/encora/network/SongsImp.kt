package com.pankaj.encora.network

import com.pankaj.encora.MainApplication
import com.pankaj.encora.R
import com.pankaj.encora.database.AppDatabase
import com.pankaj.encora.database.SongsEntity
import com.pankaj.encora.network.api.RetrofitInstance
import com.pankaj.encora.network.api.SongsService
import com.pankaj.encora.utils.Utils

/**datasource implementation
 * */
class SongsImp(private val appDatabase: AppDatabase) : SongsDataSource {
    private var songsService: SongsService = RetrofitInstance.songsService

    override suspend fun getTopSongs(): APIResult<Feed> {
        return try {
            val response = songsService.getTopSongs(Utils.SONG_LIMIT)
            APIResult(APIResult.Status.SUCCESS, response, "")
        } catch (e: Exception) {
            if (e is NetworkException) {
                APIResult(
                    APIResult.Status.ERROR, null, MainApplication.applicationContext().getString(
                        R.string.error_no_network
                    )
                )
            } else {
                APIResult(APIResult.Status.ERROR, null, e.message)
            }
        }
    }

    override suspend fun getTopSongsFromDB(): List<SongsEntity> {
        return appDatabase.entryDao().getAllEntry()
    }
}
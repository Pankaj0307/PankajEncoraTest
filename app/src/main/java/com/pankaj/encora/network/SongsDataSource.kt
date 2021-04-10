package com.pankaj.encora.network

import com.pankaj.encora.database.SongsEntity


/**interface for datasource which will be implemented in framework
 * */
interface SongsDataSource {
    suspend fun getTopSongs(): APIResult<Feed>// response data from api
    suspend fun getTopSongsFromDB(): List<SongsEntity>// data from database
}
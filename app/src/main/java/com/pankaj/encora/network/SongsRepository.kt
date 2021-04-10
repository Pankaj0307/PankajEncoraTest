package com.pankaj.encora.network

/**repository to get data
 * */
class SongsRepository(private val dataSource: SongsDataSource) {
    suspend fun getTopSongs() = dataSource.getTopSongs()
    suspend fun getTopSongsFromDB() = dataSource.getTopSongsFromDB()
}
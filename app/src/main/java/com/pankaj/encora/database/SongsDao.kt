package com.pankaj.encora.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SongsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entries: List<SongsEntity>)

    @Query("SELECT * FROM songs")
    suspend fun getAllEntry(): List<SongsEntity>
}
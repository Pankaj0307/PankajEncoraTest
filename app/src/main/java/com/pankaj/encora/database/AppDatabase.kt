package com.pankaj.encora.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pankaj.encora.utils.Utils

/**
 * application database class
 * */
@Database(entities = [SongsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Utils.DBNAME
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

    abstract fun entryDao(): SongsDao
}
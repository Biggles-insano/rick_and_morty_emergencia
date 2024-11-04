package com.uvg.lab8.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uvg.lab8.entities.CharacterEntity
import com.uvg.lab8.entities.LocationEntity

@Database(entities = [CharacterEntity::class, LocationEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
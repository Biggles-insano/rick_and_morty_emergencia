package com.uvg.lab8.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uvg.lab8.entities.LocationEntity

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)
}
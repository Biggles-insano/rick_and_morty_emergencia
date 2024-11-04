package com.uvg.lab8.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uvg.lab8.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}
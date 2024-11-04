package com.uvg.lab8.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.uvg.lab8.entities.CharacterEntity

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    @SerialName("image") val imageUrl: String
)


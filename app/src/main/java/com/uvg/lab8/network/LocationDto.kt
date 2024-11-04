package com.uvg.lab8.network

import kotlinx.serialization.Serializable
import com.uvg.lab8.entities.LocationEntity


@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
) {
    fun toEntity() = LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}
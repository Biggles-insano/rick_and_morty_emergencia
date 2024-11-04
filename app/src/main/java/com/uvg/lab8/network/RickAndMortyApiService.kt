package com.uvg.lab8.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class RickAndMortyApiService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun fetchCharacters(): List<CharacterDto> {
        println("Fetching characters from API...")
        try {
            val response: HttpResponse = client.get("https://rickandmortyapi.com/api/character")
            val result = response.body<ApiResponse>().results
            println("API response: ${result.size} characters fetched")
            return result
        } catch (e: Exception) {
            println("API request failed: ${e.message}")
            throw e
        }
    }

    @Serializable
    data class ApiResponse(
        val results: List<CharacterDto>
    )
}
package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.local.CharacterDao
import com.uvg.lab8.model.Character
import com.uvg.lab8.network.RickAndMortyApiService
import com.uvg.lab8.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CharacterListState(
    val isLoading: Boolean = true,
    val data: List<Character>? = null,
    val hasError: Boolean = false
)

class CharacterListViewModel(
    private val apiService: RickAndMortyApiService,
    private val characterDao: CharacterDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListState())
    val uiState: StateFlow<CharacterListState> = _uiState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _uiState.value = CharacterListState(isLoading = true)
            try {
                println("Trying to fetch characters from API...") // Log para inicio de llamada a API
                val apiCharacters = apiService.fetchCharacters()
                println("Fetched ${apiCharacters.size} characters from API") // Log si la API es exitosa

                val characterEntities = apiCharacters.map { dto ->
                    CharacterEntity(
                        id = dto.id,
                        name = dto.name,
                        status = dto.status,
                        species = dto.species,
                        gender = dto.gender,
                        image = dto.imageUrl
                    )
                }
                withContext(Dispatchers.IO) {
                    characterDao.deleteAllCharacters()
                    characterDao.insertAll(characterEntities)
                    println("Saved ${characterEntities.size} characters to Room")
                }

                _uiState.value = CharacterListState(
                    isLoading = false,
                    data = characterEntities.map { entity ->
                        Character(
                            id = entity.id,
                            name = entity.name,
                            status = entity.status,
                            species = entity.species,
                            gender = entity.gender,
                            image = entity.image
                        )
                    }
                )
            } catch (e: Exception) {
                println("API fetch failed, loading from Room...")
                loadCharactersFromDb()
            }
        }
    }

    private suspend fun loadCharactersFromDb() {
        val charactersFromDb = withContext(Dispatchers.IO) {
            characterDao.getAllCharacters()
        }
        println("Loaded ${charactersFromDb.size} characters from Room")
        _uiState.value = CharacterListState(
            isLoading = false,
            data = charactersFromDb.map { entity ->
                Character(
                    id = entity.id,
                    name = entity.name,
                    status = entity.status,
                    species = entity.species,
                    gender = entity.gender,
                    image = entity.image
                )
            },
            hasError = charactersFromDb.isEmpty()
        )
    }

    fun retry() {
        fetchCharacters()
    }
}
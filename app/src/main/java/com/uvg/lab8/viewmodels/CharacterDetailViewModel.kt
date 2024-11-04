package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.local.CharacterDao
import com.uvg.lab8.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)

class CharacterDetailViewModel(
    private val characterDao: CharacterDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterDetailState())
    val uiState: StateFlow<CharacterDetailState> = _uiState

    fun fetchCharacterById(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = CharacterDetailState(isLoading = true)
            try {
                val characterEntity = withContext(Dispatchers.IO) {
                    characterDao.getCharacterById(characterId)
                }

                characterEntity?.let {
                    _uiState.value = CharacterDetailState(
                        isLoading = false,
                        data = Character(
                            id = it.id,
                            name = it.name,
                            status = it.status,
                            species = it.species,
                            gender = it.gender,
                            image = it.image
                        )
                    )
                } ?: run {
                    _uiState.value = CharacterDetailState(
                        isLoading = false,
                        hasError = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = CharacterDetailState(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    fun retry(characterId: Int) {
        fetchCharacterById(characterId)
    }
}
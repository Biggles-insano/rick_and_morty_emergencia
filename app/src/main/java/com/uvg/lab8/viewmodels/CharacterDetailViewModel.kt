package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.data.CharacterDb
import com.uvg.lab8.model.Character
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)

class CharacterDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterDetailState())
    val uiState: StateFlow<CharacterDetailState> = _uiState

    fun fetchCharacterById(characterId: Int) {
        viewModelScope.launch {
            delay(2000)
            val character = CharacterDb().getCharacterById(characterId)
            _uiState.value = CharacterDetailState(isLoading = false, data = character)
        }
    }

    fun simulateError() {
        _uiState.value = _uiState.value.copy(hasError = true)
    }

    fun retry(characterId: Int) {
        _uiState.value = CharacterDetailState(isLoading = true)
        fetchCharacterById(characterId)
    }
}

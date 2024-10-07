package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.data.CharacterDb
import com.uvg.lab8.model.Character
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CharacterListState(
    val isLoading: Boolean = true,
    val data: List<Character>? = null,
    val hasError: Boolean = false
)

class CharacterListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterListState())
    val uiState: StateFlow<CharacterListState> = _uiState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            delay(4000)
            _uiState.value = CharacterListState(
                isLoading = false,
                data = CharacterDb().getAllCharacters()
            )
        }
    }

    fun simulateError() {
        _uiState.value = _uiState.value.copy(hasError = true)
    }

    fun retry() {
        _uiState.value = CharacterListState(isLoading = true)
        fetchCharacters()
    }
}

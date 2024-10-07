package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.data.LocationDb
import com.uvg.lab8.model.Location
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LocationListState(
    val isLoading: Boolean = true,
    val data: List<Location>? = null,
    val hasError: Boolean = false
)

class LocationListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LocationListState())
    val uiState: StateFlow<LocationListState> = _uiState

    init {
        fetchLocations()
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            delay(4000)
            _uiState.value = LocationListState(
                isLoading = false,
                data = LocationDb().getAllLocations()
            )
        }
    }

    fun simulateError() {
        _uiState.value = _uiState.value.copy(hasError = true)
    }

    fun retry() {
        _uiState.value = LocationListState(isLoading = true)
        fetchLocations()
    }
}

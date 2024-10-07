package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.data.LocationDb
import com.uvg.lab8.model.Location
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LocationDetailState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)

class LocationDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LocationDetailState())
    val uiState: StateFlow<LocationDetailState> = _uiState

    fun fetchLocationById(locationId: Int) {
        viewModelScope.launch {
            delay(2000)
            val location = LocationDb().getLocationById(locationId)
            _uiState.value = LocationDetailState(isLoading = false, data = location)
        }
    }

    fun simulateError() {
        _uiState.value = _uiState.value.copy(hasError = true)
    }

    fun retry(locationId: Int) {
        _uiState.value = LocationDetailState(isLoading = true)
        fetchLocationById(locationId)
    }
}

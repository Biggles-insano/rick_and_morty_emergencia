package com.uvg.lab8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab8.entities.LocationEntity
import com.uvg.lab8.local.LocationDao
import com.uvg.lab8.network.RickAndMortyApiService
//import com.uvg.lab8.network.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LocationListState(
    val isLoading: Boolean = true,
    val data: List<LocationEntity>? = null,
    val hasError: Boolean = false
)

class LocationListViewModel(
    private val apiService: RickAndMortyApiService,
    private val locationDao: LocationDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationListState())
    val uiState: StateFlow<LocationListState> = _uiState

    init {
        fetchLocations()
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            try {
               // val locations = apiService.fetchLocations().map { it.toEntity() }
               // locationDao.insertAll(locations)
               // _uiState.value = LocationListState(isLoading = false, data = locations)
            } catch (e: Exception) {
                val cachedLocations = locationDao.getAllLocations()
                _uiState.value = LocationListState(isLoading = false, data = cachedLocations, hasError = cachedLocations.isEmpty())
            }
        }
    }

    fun retry() {
        _uiState.value = LocationListState(isLoading = true)
        fetchLocations()
    }
}
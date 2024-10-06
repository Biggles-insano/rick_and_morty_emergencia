package com.uvg.lab8.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.lab8.viewmodels.LocationListViewModel
import com.uvg.lab8.model.Location

@Composable
fun LocationsScreen(navController: NavController, viewModel: LocationListViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Locations") })
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingLayout { viewModel.simulateError() }
            }
            uiState.hasError -> {
                ErrorLayout { viewModel.retry() }
            }
            else -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.padding(16.dp)
                ) {
                    uiState.data?.let { locations ->
                        items(locations) { location ->
                            LocationRow(location = location) {
                                navController.navigate("location_detail/${location.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationRow(location: Location, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Text(text = location.name, style = MaterialTheme.typography.h6)
            Text(text = "Type: ${location.type}", style = MaterialTheme.typography.body2)
            Text(text = "Dimension: ${location.dimension}", style = MaterialTheme.typography.body2)
        }
    }
}
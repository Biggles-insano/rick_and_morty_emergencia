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
import androidx.compose.ui.Alignment

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
           
                LocationLoadingLayout { viewModel.simulateError() }
            }
            uiState.hasError -> {
              
                LocationErrorLayout { viewModel.retry() }
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


@Composable
fun LocationLoadingLayout(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() } 
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading location list...")
        }
    }
}


@Composable
fun LocationErrorLayout(onRetry: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Error loading location list")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

package com.uvg.lab8.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.uvg.lab8.viewmodels.LocationDetailViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

@Composable
fun LocationDetailScreen(
    navController: NavController,
    locationId: Int,
    viewModel: LocationDetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLocationById(locationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Details") },
                navigationIcon = {
                    Text("Back", modifier = Modifier.clickable { navController.navigateUp() })
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingLayout { viewModel.fetchLocationById(locationId) }
            }
            uiState.hasError -> {
                ErrorLayout { viewModel.fetchLocationById(locationId) }
            }
            else -> {
                uiState.data?.let { location ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Name: ${location.name}", style = MaterialTheme.typography.h5)
                        Text(text = "Type: ${location.type}")
                        Text(text = "Dimension: ${location.dimension}")
                    }
                }
            }
        }
    }
}
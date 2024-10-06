package com.uvg.lab8.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.lab8.viewmodels.CharacterDetailViewModel
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable

@Composable
fun CharacterDetailScreen(navController: NavController, characterId: Int, viewModel: CharacterDetailViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCharacterById(characterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Details") },
                navigationIcon = {
                    Text("Back", modifier = Modifier.clickable { navController.navigateUp() })
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingLayout { viewModel.simulateError() }
            }
            uiState.hasError -> {
                ErrorLayout { viewModel.retry(characterId) }
            }
            else -> {
                uiState.data?.let { character ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Name: ${character.name}", style = MaterialTheme.typography.h5)
                        Text(text = "Species: ${character.species}")
                        Text(text = "Status: ${character.status}")
                        Text(text = "Gender: ${character.gender}")
                    }
                }
            }
        }
    }
}
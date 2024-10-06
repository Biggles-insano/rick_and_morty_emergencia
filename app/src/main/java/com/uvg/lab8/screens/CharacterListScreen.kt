package com.uvg.lab8.screens

import com.uvg.lab8.model.Character
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
import com.uvg.lab8.viewmodels.CharacterListViewModel
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun CharacterListScreen(navController: NavController, viewModel: CharacterListViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Characters") })
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
                    uiState.data?.let { characters ->
                        items(characters) { character ->
                            CharacterRow(character = character) {
                                navController.navigate("character_detail/${character.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingLayout(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading...")
        }
    }
}

@Composable
fun ErrorLayout(onRetry: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Error loading data")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun CharacterRow(character: Character, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberImagePainter(character.image),
            contentDescription = character.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = character.name, style = MaterialTheme.typography.h6)
            Text(text = "${character.species} - ${character.status}", style = MaterialTheme.typography.body2)
        }
    }
}
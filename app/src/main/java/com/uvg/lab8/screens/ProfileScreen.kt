package com.uvg.lab8.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.lab8.R

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.yo),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Samuel Mejía", style = MaterialTheme.typography.h5)
            Text(text = "Carné: 23442", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }) {
                Text("Cerrar sesión")
            }
        }
    }
}
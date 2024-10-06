package com.uvg.lab8.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.lab8.R

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rick_morty_logo),
            contentDescription = "Rick and Morty Logo"
        )
        Button(
            onClick = {
                navController.navigate("character_list") {
                    popUpTo("login") { inclusive = true } 
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Entrar")
        }

        Text(
            text = "Samuel Mejía - #23442",
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

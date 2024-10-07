package com.uvg.lab8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uvg.lab8.screens.*
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyApp()
        }
    }
}

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

  
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            
            if (currentRoute != "login") {
                RickAndMortyBottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",  
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") { LoginScreen(navController) } 
            composable("character_list") { CharacterListScreen(navController) }
            composable("locations") { LocationsScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable(
                "character_detail/{characterId}",
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId")
                characterId?.let { CharacterDetailScreen(navController, it) }
            }
            composable(
                "location_detail/{locationId}",
                arguments = listOf(navArgument("locationId") { type = NavType.IntType })
            ) { backStackEntry ->
                val locationId = backStackEntry.arguments?.getInt("locationId")
                locationId?.let { LocationDetailScreen(navController, it) }
            }
        }
    }
}

@Composable
fun RickAndMortyBottomNavigation(navController: NavHostController) {
    BottomNavigation {
        BottomNavigationItem(
            icon = {}, 
            label = { Text("Characters") },
            selected = false,
            onClick = { navController.navigate("character_list") }
        )
        BottomNavigationItem(
            icon = {}, 
            label = { Text("Locations") },
            selected = false,
            onClick = { navController.navigate("locations") }
        )
        BottomNavigationItem(
            icon = {}, 
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("profile") }
        )
    }
}

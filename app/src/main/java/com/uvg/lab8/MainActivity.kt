package com.uvg.lab8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.uvg.lab8.local.AppDatabase
import com.uvg.lab8.network.RickAndMortyApiService
import com.uvg.lab8.viewmodels.CharacterListViewModel
import com.uvg.lab8.viewmodels.CharacterDetailViewModel
import com.uvg.lab8.viewmodels.LocationListViewModel
import com.uvg.lab8.viewmodels.LocationDetailViewModel
import com.uvg.lab8.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val characterDao = database.characterDao()
        val locationDao = database.locationDao()
        val apiService = RickAndMortyApiService()

        val characterListViewModel = CharacterListViewModel(apiService, characterDao)
        val characterDetailViewModel = CharacterDetailViewModel(characterDao)
        val locationListViewModel = LocationListViewModel(apiService, locationDao)

        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            Scaffold(
                bottomBar = {
                    if (currentRoute != "login") {
                        BottomNavigationBar(navController)
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("login") { LoginScreen(navController) }
                    composable("character_list") { CharacterListScreen(navController, characterListViewModel) }
                    composable("location_list") { LocationListScreen(navController, locationListViewModel) }
                    composable("profile") { ProfileScreen(navController) }
                    composable(
                        "character_detail/{characterId}",
                        arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val characterId = backStackEntry.arguments?.getInt("characterId")
                        characterId?.let {
                            CharacterDetailScreen(navController, it, characterDetailViewModel)
                        }
                    }
                    composable(
                        "location_detail/{locationId}",
                        arguments = listOf(navArgument("locationId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val locationId = backStackEntry.arguments?.getInt("locationId")
                        locationId?.let {
                         //   LocationDetailScreen(navController, it, locationDetailViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
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
            onClick = { navController.navigate("location_list") }
        )
        BottomNavigationItem(
            icon = {},
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("profile") }
        )
    }
}
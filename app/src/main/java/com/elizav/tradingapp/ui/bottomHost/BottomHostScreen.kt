package com.elizav.tradingapp.ui.bottomHost

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.graph.BottomHostGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomHostScreen() {
    val navController = rememberNavController()
    val viewModel: BottomHostViewModel = hiltViewModel()
    val clientState by viewModel.clientState.collectAsState()
    val items = listOf(
        Destination.ProfileDestination,
        Destination.SignalsListDestination,
        Destination.PromoListDestination
    )
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController, items = items) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            BottomHostGraph(navController = navController, client = clientState)
        }
    }
}
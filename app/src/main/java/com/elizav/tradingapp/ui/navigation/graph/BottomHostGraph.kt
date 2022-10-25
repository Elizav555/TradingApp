package com.elizav.tradingapp.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.NavHost
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.composable
import com.elizav.tradingapp.ui.profile.ProfileScreen
import com.elizav.tradingapp.ui.promo.PromoListScreen
import com.elizav.tradingapp.ui.signals.SignalsListScreen

@Composable
fun BottomHostGraph(navController: NavHostController, client: Client?) {
    NavHost(
        navController = navController,
        startDestination = Destination.SignalsListDestination,
        route = Route.BottomGraphRoute()
    ) {
        composable(destination = Destination.ProfileDestination) {
            ProfileScreen(navController = navController, client = client)
        }
        composable(destination = Destination.SignalsListDestination) {
            SignalsListScreen(navController = navController, client = client)
        }
        composable(destination = Destination.PromoListDestination) {
            PromoListScreen(navController = navController)
        }
    }
}
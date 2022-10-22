package com.elizav.tradingapp.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.elizav.tradingapp.ui.auth.AuthScreen
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.composable
import com.elizav.tradingapp.ui.navigation.navigation

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = Destination.AuthDestination,
        route = Route.AuthGraphRoute
    ) {
        composable(destination = Destination.AuthDestination) {
            AuthScreen(navController = navController)
        }
    }
}
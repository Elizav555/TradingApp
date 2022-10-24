package com.elizav.tradingapp.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.elizav.tradingapp.ui.bottomHost.BottomHostScreen
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.NavHost
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.composable
import com.elizav.tradingapp.ui.web.WebViewScreen

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Route.RootRoute,
        startDestination = Destination.AuthGraphDestination
    ) {
        authGraph(navController = navController)
        composable(destination = Destination.BottomGraphDestination) {
            BottomHostScreen()
        }
        composable(destination = Destination.WebViewDestination) {
            WebViewScreen(navController = navController)
        }
    }
}

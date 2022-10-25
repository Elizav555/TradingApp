package com.elizav.tradingapp.ui.main

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elizav.tradingapp.ui.navigation.graph.RootGraph
import com.elizav.tradingapp.ui.navigation.navigator.NavigationIntent
import com.elizav.tradingapp.ui.theme.TradingAppTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel<MainViewModel>()
    LaunchedEffect(key1 = Unit) {
        mainViewModel.authState.distinctUntilChanged().collect {
            if (!it) {
                mainViewModel.onEvent(MainEvent.onTokenExpired)
            }
        }
    }
    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )
    TradingAppTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RootGraph(navController = navController)
        }
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route.value, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route.value) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}
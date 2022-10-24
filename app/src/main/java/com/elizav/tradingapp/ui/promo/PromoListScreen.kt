package com.elizav.tradingapp.ui.promo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elizav.tradingapp.ui.promo.state.PromoListEvent
import com.elizav.tradingapp.ui.promo.state.PromoListScreenState
import com.elizav.tradingapp.ui.utils.Command
import com.elizav.tradingapp.ui.utils.Loading

@Composable
fun PromoListScreen(navController: NavController) {
    val promoListViewModel: PromoListViewModel = hiltViewModel()
    val uiState by promoListViewModel.uiState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val onPromoClick = { link: String ->
        promoListViewModel.onEvent(
            PromoListEvent.OnPromoClick(link)
        )
    }
    LaunchedEffect(key1 = Unit) {
        promoListViewModel.commandEvent.collect {
            when (it) {
                is Command.ErrorCommand -> {
                    snackbarHostState.showSnackbar(
                        message = it.errorMessage,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    PromoListContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onPromoClick = onPromoClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PromoListContent(
    uiState: PromoListScreenState,
    snackbarHostState: SnackbarHostState,
    onPromoClick: (String) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (uiState.isLoading) {
            Loading(Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                List(uiState.promoList.size) { index ->
                    PromoListItem(uiState.promoList[index], onPromoClick)
                }
            }
        }
    }
}

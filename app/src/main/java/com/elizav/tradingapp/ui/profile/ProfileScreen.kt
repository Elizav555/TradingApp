package com.elizav.tradingapp.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elizav.tradingapp.R
import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.ui.profile.state.ProfileEvent
import com.elizav.tradingapp.ui.profile.state.ProfileScreenState
import com.elizav.tradingapp.ui.utils.Command
import com.elizav.tradingapp.ui.utils.Loading

@Composable
fun ProfileScreen(navController: NavController, client: Client?) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    client?.let { profileViewModel.onEvent(ProfileEvent.InitClientEvent(it)) }

    val uiState by profileViewModel.uiState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val onLogoutClick = {
        profileViewModel.onEvent(
            ProfileEvent.LogoutEvent
        )
    }
    LaunchedEffect(key1 = Unit) {
        profileViewModel.commandEvent.collect {
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

    ProfileContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onLogoutClick = onLogoutClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    uiState: ProfileScreenState,
    snackbarHostState: SnackbarHostState,
    onLogoutClick: () -> Unit
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
                uiState.clientInfo?.run {
                    Text(text = lastFourNumbPhone)
                    Text(text = accountInfo.city)
                    Text(text = accountInfo.address)
                    Text(text = accountInfo.name)
                    Text(text = accountInfo.country)
                    Text(text = accountInfo.phone)
                    Text(text = accountInfo.zipCode)
                    Text(text = accountInfo.balance.toString())
                    Text(text = accountInfo.currency.toString())
                    Text(text = accountInfo.currentTradesCount.toString())
                    Text(text = accountInfo.currentTradesVolume.toString())
                }
                Button(onClick = onLogoutClick) {
                    Text(
                        text = stringResource(id = R.string.logout),
                    )
                }
            }
        }
    }
}
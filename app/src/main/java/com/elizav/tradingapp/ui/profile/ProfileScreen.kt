package com.elizav.tradingapp.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.utils.Command
import com.elizav.tradingapp.ui.profile.state.ProfileEvent
import com.elizav.tradingapp.ui.profile.state.ProfileScreenState
import com.elizav.tradingapp.ui.widgets.Loading
import com.elizav.tradingapp.ui.widgets.ReadonlyTextField

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
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.clientInfo?.accountInfo?.name
                            ?: stringResource(id = R.string.profile)
                    )
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = stringResource(id = R.string.logout)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (uiState.isLoading) {
            Loading(Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                uiState.clientInfo?.run {
                    ReadonlyTextField(
                        value = lastFourNumbPhone,
                        labelText = stringResource(R.string.last_four_numb)
                    )

                    ReadonlyTextField(
                        value = accountInfo.city,
                        labelText = stringResource(R.string.city)
                    )

                    ReadonlyTextField(
                        value = accountInfo.address,
                        labelText = stringResource(R.string.address)
                    )

                    ReadonlyTextField(
                        value = accountInfo.phone,
                        labelText = stringResource(R.string.phone_hash)
                    )

                    ReadonlyTextField(
                        value = accountInfo.zipCode,
                        labelText = stringResource(R.string.zipCode)
                    )
                    ReadonlyTextField(
                        value = accountInfo.balance.toString(),
                        labelText = stringResource(R.string.balance)
                    )

                    ReadonlyTextField(
                        value = accountInfo.currency.toString(),
                        labelText = stringResource(R.string.currency)
                    )

                    ReadonlyTextField(
                        value = accountInfo.currentTradesCount.toString(),
                        labelText = stringResource(R.string.current_trades_count)
                    )

                    ReadonlyTextField(
                        value = accountInfo.currentTradesVolume.toString(),
                        labelText = stringResource(R.string.current_trades_volume)
                    )
                }
            }
        }
    }
}
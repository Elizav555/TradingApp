package com.elizav.tradingapp.ui.auth

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
import com.elizav.tradingapp.ui.auth.state.AuthEvent
import com.elizav.tradingapp.ui.auth.state.AuthScreenState
import com.elizav.tradingapp.domain.utils.Command
import com.elizav.tradingapp.ui.widgets.Loading

@Composable
fun AuthScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val uiState by authViewModel.uiState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val onLoginClick = { login: String, password: String ->
        authViewModel.onEvent(
            AuthEvent.SignInEvent(
                login,
                password
            )
        )
    }
    LaunchedEffect(key1 = Unit) {
        authViewModel.commandEvent.collect {
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

    AuthContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onLoginClick = onLoginClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthContent(
    uiState: AuthScreenState,
    snackbarHostState: SnackbarHostState,
    onLoginClick: (login: String, password: String) -> Unit
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
                Button(onClick = { onLoginClick("", "") }) {
                    Text(
                        text = stringResource(id = R.string.login),
                    )
                }
            }
        }
    }
}
package com.elizav.tradingapp.ui.signals

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
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.Pair
import com.elizav.tradingapp.ui.signals.state.SignalsListEvent
import com.elizav.tradingapp.ui.signals.state.SignalsListScreenState
import com.elizav.tradingapp.domain.model.utils.Command
import com.elizav.tradingapp.ui.widgets.Loading
import java.util.Calendar

@Composable
fun SignalsListScreen(navController: NavController, client: Client?) {
    val signalsListViewModel: SignalsListViewModel = hiltViewModel()
    client?.let { signalsListViewModel.onEvent(SignalsListEvent.InitClientEvent(it)) }
    val uiState by signalsListViewModel.uiState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val onGetSignalsClick = { pairs: List<Pair>, from: Calendar, to: Calendar ->
        signalsListViewModel.onEvent(
            SignalsListEvent.GetSignalsList(pairs, from, to)
        )
    }
    LaunchedEffect(key1 = Unit) {
        signalsListViewModel.commandEvent.collect {
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

    SignalsListContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onGetSignalsClick = onGetSignalsClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignalsListContent(
    uiState: SignalsListScreenState,
    snackbarHostState: SnackbarHostState,
    onGetSignalsClick: (List<Pair>, Calendar, Calendar) -> Unit
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
                Button(onClick = {
                    onGetSignalsClick(
                        listOf(Pair.GBPUSD, Pair.EURUSD),
                        Calendar.getInstance().apply { timeInMillis = 1479860023 },
                        Calendar.getInstance().apply { timeInMillis = 1480066860 })
                }) {
                    Text(
                        text = stringResource(id = R.string.get_signals),
                    )
                }
                List(uiState.signals.size) { index ->
                    SignalListItem(uiState.signals[index])
                }
            }
        }
    }
}

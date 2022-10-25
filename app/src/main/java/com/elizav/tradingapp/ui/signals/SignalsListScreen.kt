package com.elizav.tradingapp.ui.signals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elizav.tradingapp.R
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.SignalPair
import com.elizav.tradingapp.domain.utils.Command
import com.elizav.tradingapp.ui.signals.state.SignalsListEvent
import com.elizav.tradingapp.ui.signals.state.SignalsListScreenState
import com.elizav.tradingapp.ui.widgets.Loading
import com.elizav.tradingapp.ui.widgets.SignalsDialog

@Composable
fun SignalsListScreen(navController: NavController, client: Client?) {
    val signalsListViewModel: SignalsListViewModel = hiltViewModel()
    client?.let { signalsListViewModel.onEvent(SignalsListEvent.InitClientEvent(it)) }
    val uiState by signalsListViewModel.uiState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val dialogSnackbarHostState = remember {
        SnackbarHostState()
    }
    val onGetSignalsClick = {
        signalsListViewModel.onEvent(
            SignalsListEvent.GetSignalsList
        )
    }
    val onPairChange = { isChecked: Boolean, pair: SignalPair ->
        signalsListViewModel.onEvent(
            SignalsListEvent.UpdatePairs(isChecked, pair)
        )
    }
    val onFromChange: ((String) -> Unit) = { newValue: String ->
        signalsListViewModel.onEvent(
            SignalsListEvent.UpdateFromString(newValue)
        )
    }
    val onToChange: ((String) -> Unit) = { newValue: String ->
        signalsListViewModel.onEvent(
            SignalsListEvent.UpdateToString(newValue)
        )
    }

    val onHandleDialog: ((Boolean) -> Unit) = { isOpened: Boolean ->
        signalsListViewModel.onEvent(
            SignalsListEvent.HandleDialog(isOpened)
        )
    }

    LaunchedEffect(key1 = Unit) {
        signalsListViewModel.commandEvent.collect {
            when (it) {
                is Command.ErrorCommand -> {
                    if (uiState.isDialogOpened) {
                        dialogSnackbarHostState.showSnackbar(
                            message = it.errorMessage,
                            withDismissAction = true
                        )
                    } else snackbarHostState.showSnackbar(
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
        dialogSnackbarHostState = dialogSnackbarHostState,
        onGetSignalsClick = onGetSignalsClick,
        onPairChange = onPairChange,
        onFromChange = onFromChange,
        onToChange = onToChange,
        onHandleDialog = onHandleDialog,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignalsListContent(
    uiState: SignalsListScreenState,
    snackbarHostState: SnackbarHostState,
    dialogSnackbarHostState: SnackbarHostState,
    onGetSignalsClick: () -> Unit,
    onPairChange: (Boolean, SignalPair) -> Unit,
    onFromChange: (String) -> Unit,
    onToChange: (String) -> Unit,
    onHandleDialog: (Boolean) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (uiState.isLoading) {
            Loading(Modifier.padding(padding))
        } else if (uiState.isDialogOpened) {
            SignalsDialog(
                uiState = uiState,
                onPairChange = onPairChange,
                onFromChange = onFromChange,
                onToChange = onToChange,
                onSubmitDialog = onGetSignalsClick,
                onCloseDialog = { onHandleDialog(false) },
                snackbarHostState = dialogSnackbarHostState
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onHandleDialog(true) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.get_signals),
                    )
                }
                if (uiState.signals.isEmpty()) {
                    Text(text = stringResource(R.string.signals_empty))
                } else {
                    val listState = rememberLazyListState()
                    LazyColumn(
                        state = listState
                    ) {
                        items(
                            count = uiState.signals.size,
                            key = { index ->
                                uiState.signals[index].id
                            })
                        { index -> SignalListItem(signal = uiState.signals[index]) }
                    }
                }
            }
        }
    }
}

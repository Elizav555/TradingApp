package com.elizav.tradingapp.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elizav.tradingapp.R
import com.elizav.tradingapp.domain.model.signal.SignalPair
import com.elizav.tradingapp.ui.signals.ChoseSignalParamsInputs
import com.elizav.tradingapp.ui.signals.state.SignalsListScreenState


@Composable
fun SignalsDialog(
    uiState: SignalsListScreenState,
    onPairChange: (Boolean, SignalPair) -> Unit,
    onFromChange: (String) -> Unit,
    onToChange: (String) -> Unit,
    onSubmitDialog: () -> Unit,
    onCloseDialog: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    AlertDialog(
        modifier=Modifier.padding(top = 32.dp, bottom = 48.dp),
        onDismissRequest = onCloseDialog,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SnackbarHost(hostState = snackbarHostState)
                ChoseSignalParamsInputs(
                    uiState = uiState,
                    onPairChange = onPairChange,
                    onFromChange = onFromChange,
                    onToChange = onToChange,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onSubmitDialog
            ) {
                Text(stringResource(R.string.get_signals))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCloseDialog
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview(){
    SignalsDialog(
        uiState = SignalsListScreenState(false),
        onPairChange = {_,_->},
        onFromChange = {},
        onToChange = {},
        onSubmitDialog = {  },
        onCloseDialog = { },
        snackbarHostState = SnackbarHostState()
    )
}
package com.elizav.tradingapp.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elizav.tradingapp.R
import com.elizav.tradingapp.domain.model.signal.SignalPair
import com.elizav.tradingapp.ui.signals.state.SignalsListScreenState

@Composable
fun ChoseSignalParamsInputs(
    modifier: Modifier = Modifier,
    uiState: SignalsListScreenState,
    onPairChange: (Boolean, SignalPair) -> Unit,
    onFromChange: (String) -> Unit,
    onToChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        GroupedPairsCheckbox(
            signalPairList = SignalPair.values().toList(),
            onChecked = onPairChange,
            signalPairs = uiState.signalPairs
        )
        DateTextField(
            modifier.padding(8.dp),
            text = uiState.fromString,
            onTextChange = onFromChange,
            label = stringResource(R.string.from_date)
        )
        DateTextField(
            modifier.padding(8.dp),
            text = uiState.toString,
            onTextChange = onToChange,
            label = stringResource(R.string.to_date)
        )
    }
}

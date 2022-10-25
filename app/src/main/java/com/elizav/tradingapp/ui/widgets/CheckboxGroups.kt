package com.elizav.tradingapp.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elizav.tradingapp.domain.model.signal.SignalPair

@Composable
fun GroupedPairsCheckbox(
    modifier: Modifier = Modifier,
    signalPairList: List<SignalPair>,
    signalPairs:Set<SignalPair>,
    onChecked: (Boolean, SignalPair) -> Unit
) {
    signalPairList.forEach { pair ->
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = signalPairs.contains(pair),
                onCheckedChange = {
                    onChecked(it, pair)
                },
                enabled = true,
            )
            Text(text = pair.name)
        }
    }
}
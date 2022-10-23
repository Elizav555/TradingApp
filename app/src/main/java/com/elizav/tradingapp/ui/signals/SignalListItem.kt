package com.elizav.tradingapp.ui.signals

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elizav.tradingapp.domain.model.Signal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignalListItem(signal: Signal, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier,
        headlineText = {
            Text(text = signal.id.toString())
        }
    )
}

package com.elizav.tradingapp.ui.signals.state

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.Signal
import com.elizav.tradingapp.domain.model.signal.SignalPair

data class SignalsListScreenState(
    val isLoading: Boolean,
    val client: Client? = null,
    val signals: List<Signal> = emptyList(),
    val signalPairs: Set<SignalPair> = setOf(
        SignalPair.EURUSD,
        SignalPair.GBPUSD
    ),
    val fromString: String = "10/10/2020",
    val toString: String = "10/10/2022",
    val isDialogOpened: Boolean = false
)
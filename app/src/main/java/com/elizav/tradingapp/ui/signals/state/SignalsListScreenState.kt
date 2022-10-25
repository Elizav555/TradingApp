package com.elizav.tradingapp.ui.signals.state

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.Signal

data class SignalsListScreenState(
    val isLoading: Boolean,
    val client: Client? = null,
    val signals: List<Signal> = emptyList()
)
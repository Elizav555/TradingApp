package com.elizav.tradingapp.ui.signals.state

import com.elizav.tradingapp.domain.model.client.Client

sealed class SignalsListEvent {
    object GetSignalsList : SignalsListEvent()
    data class HandleDialog(val isOpened: Boolean) : SignalsListEvent()
    data class UpdatePairs(
        val isChecked: Boolean,
        val signalPair: com.elizav.tradingapp.domain.model.signal.SignalPair
    ) : SignalsListEvent()

    data class UpdateFromString(
        val newValue: String
    ) : SignalsListEvent()

    data class UpdateToString(
        val newValue: String
    ) : SignalsListEvent()

    data class InitClientEvent(val client: Client) : SignalsListEvent()

}

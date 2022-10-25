package com.elizav.tradingapp.ui.signals.state

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.Pair
import java.util.Calendar

sealed class SignalsListEvent {
    data class GetSignalsList(val pairs: List<Pair>, val from: Calendar, val to: Calendar) :
        SignalsListEvent()

    data class InitClientEvent(val client: Client) : SignalsListEvent()
}

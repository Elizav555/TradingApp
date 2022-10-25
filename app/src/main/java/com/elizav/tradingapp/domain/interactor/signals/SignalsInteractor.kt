package com.elizav.tradingapp.domain.interactor.signals

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.Pair
import com.elizav.tradingapp.domain.model.signal.Signal
import java.util.Calendar

interface SignalsInteractor {
    suspend fun getSignals(
        client: Client,
        pairs: List<Pair>,
        from: Calendar,
        to: Calendar
    ): Result<List<Signal>>
}
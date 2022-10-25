package com.elizav.tradingapp.domain.interactor.signals

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.SignalPair
import com.elizav.tradingapp.domain.model.signal.Signal

interface SignalsInteractor {
    suspend fun getSignals(
        client: Client,
        signalPairs: List<SignalPair>,
        from: Long,
        to: Long
    ): Result<List<Signal>>
}
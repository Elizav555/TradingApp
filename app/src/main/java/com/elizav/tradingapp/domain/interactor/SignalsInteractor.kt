package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.domain.model.Pair
import com.elizav.tradingapp.domain.model.Signal
import java.util.Calendar
import java.util.Date

interface SignalsInteractor {
    suspend fun getSignals(
        client: Client,
        pairs: List<Pair>,
        from: Calendar,
        to: Calendar
    ): Result<List<Signal>>
}
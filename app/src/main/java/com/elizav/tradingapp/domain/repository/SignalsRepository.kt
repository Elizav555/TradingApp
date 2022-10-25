package com.elizav.tradingapp.domain.repository

import com.elizav.tradingapp.domain.model.signal.Signal

interface SignalsRepository {
    suspend fun getSignals(
        login: String,
        partnerToken: String,
        pairs: String,
        from: Long,
        to: Long
    ): Result<List<Signal>>
}
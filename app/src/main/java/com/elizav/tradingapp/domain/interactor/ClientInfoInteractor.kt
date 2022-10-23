package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.domain.model.ClientInfo

interface ClientInfoInteractor {
    suspend fun getClientInfo(client: Client): Result<ClientInfo>
}
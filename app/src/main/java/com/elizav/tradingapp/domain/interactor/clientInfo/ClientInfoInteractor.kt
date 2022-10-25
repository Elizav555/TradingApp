package com.elizav.tradingapp.domain.interactor.clientInfo

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.client.ClientInfo

interface ClientInfoInteractor {
    suspend fun getClientInfo(client: Client): Result<ClientInfo>
}
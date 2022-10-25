package com.elizav.tradingapp.data.mapper

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.data.model.client.Client as ClientData

object ClientMapper {
    fun ClientData.toDomain() = Client(
        login = login,
        partnerToken = partnerToken,
        peanutToken = peanutToken
    )

    fun Client.toData() = ClientData(
        login = login,
        partnerToken = partnerToken,
        peanutToken = peanutToken
    )
}
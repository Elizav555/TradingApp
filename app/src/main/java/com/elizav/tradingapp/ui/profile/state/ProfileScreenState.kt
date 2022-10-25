package com.elizav.tradingapp.ui.profile.state

import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.client.ClientInfo

data class ProfileScreenState(
    val isLoading: Boolean,
    val client: Client? = null,
    val clientInfo: ClientInfo? = null
)
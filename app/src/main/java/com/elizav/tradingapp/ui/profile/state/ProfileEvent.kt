package com.elizav.tradingapp.ui.profile.state

import com.elizav.tradingapp.domain.model.client.Client

sealed class ProfileEvent {
    object LogoutEvent : ProfileEvent()
    data class InitClientEvent(val client: Client):ProfileEvent()
}

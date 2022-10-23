package com.elizav.tradingapp.ui.profile.state

sealed class ProfileEvent {
    object LogoutEvent : ProfileEvent()
}

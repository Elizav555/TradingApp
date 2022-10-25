package com.elizav.tradingapp.ui.main

sealed class MainEvent {
    object onTokenExpired : MainEvent()
}

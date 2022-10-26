package com.elizav.tradingapp.ui.web.state

sealed class WebViewEvent() {
    object GoBackEvent : WebViewEvent()
    data class HandleLoading(val isLoading: Boolean) : WebViewEvent()
}
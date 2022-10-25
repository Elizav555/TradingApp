package com.elizav.tradingapp.ui.promo.state

sealed class PromoListEvent {
    data class OnPromoClick(val link: String) : PromoListEvent()
}

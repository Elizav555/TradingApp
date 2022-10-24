package com.elizav.tradingapp.ui.promo.state

import com.elizav.tradingapp.domain.model.Client

sealed class PromoListEvent {
    data class OnPromoClick(val link: String) : PromoListEvent()
}

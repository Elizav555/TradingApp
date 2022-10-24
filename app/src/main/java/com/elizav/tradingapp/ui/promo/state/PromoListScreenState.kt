package com.elizav.tradingapp.ui.promo.state

import com.elizav.tradingapp.domain.model.Promo

data class PromoListScreenState(
    val isLoading: Boolean,
    val promoList: List<Promo> = emptyList()
)
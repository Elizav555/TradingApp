package com.elizav.tradingapp.ui.promo.state

import com.elizav.tradingapp.domain.model.Client

data class PromoListScreenState(
    val isLoading: Boolean,
    val promoList: List<String> = emptyList()
)
package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.domain.model.Promo

interface PromoInteractor {
    suspend fun getPromo(lang: String = "en"): Result<List<Promo>>
}
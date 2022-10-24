package com.elizav.tradingapp.domain.repository

import com.elizav.tradingapp.domain.model.Promo

interface PromoRepository {
    suspend fun getPromo(lang: String): Result<List<Promo>>
}
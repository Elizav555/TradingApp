package com.elizav.tradingapp.domain.interactor

interface PromoInteractor {
    suspend fun getPromo(lang: String = "en"): Result<String>
}
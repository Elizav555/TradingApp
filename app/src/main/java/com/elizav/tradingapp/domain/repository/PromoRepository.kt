package com.elizav.tradingapp.domain.repository

interface PromoRepository {
    suspend fun getPromo(lang: String): Result<String>
}
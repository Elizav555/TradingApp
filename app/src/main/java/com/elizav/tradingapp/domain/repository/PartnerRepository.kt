package com.elizav.tradingapp.domain.repository

interface PartnerRepository {
    suspend fun getSignals()
}
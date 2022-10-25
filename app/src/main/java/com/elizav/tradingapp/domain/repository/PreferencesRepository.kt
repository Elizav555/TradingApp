package com.elizav.tradingapp.domain.repository

import com.elizav.tradingapp.domain.model.client.Client

interface PreferencesRepository {
    suspend fun saveClient(key: String, client: Client)
    suspend fun getClient(key: String): Client?
    suspend fun deleteClient(key: String)
}
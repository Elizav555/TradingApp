package com.elizav.tradingapp.domain.interactor.preferences

import com.elizav.tradingapp.domain.model.client.Client

interface PreferencesInteractor {
    suspend fun saveClient(client: Client)
    suspend fun getClient(): Client?
    suspend fun deleteClient()
}
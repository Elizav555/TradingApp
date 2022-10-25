package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.ClientMapper.toData
import com.elizav.tradingapp.data.mapper.ClientMapper.toDomain
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.preferences.Preferences
import com.elizav.tradingapp.domain.repository.PreferencesRepository
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import com.elizav.tradingapp.data.model.client.Client as ClientData

class PreferencesRepositoryImpl @Inject constructor(
    private val preferences: Preferences
) : PreferencesRepository {
    override suspend fun saveClient(key: String, client: Client) {
        preferences.setItem(key, client.toData())
    }

    override suspend fun getClient(key: String): Client? =
        preferences.getItem<ClientData>(key, object : TypeToken<ClientData?>() {}.type)
            ?.toDomain()

    override suspend fun deleteClient(key: String) {
        preferences.deleteItem<ClientData>(key)
    }
}
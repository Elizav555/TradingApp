package com.elizav.tradingapp.domain.interactor.preferences

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.repository.PreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PreferencesInteractorImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : PreferencesInteractor {
    override suspend fun saveClient(client: Client) =
        withContext(coroutineDispatcher) {
            preferencesRepository.saveClient(PREFS_KEY, client)
        }

    override suspend fun getClient() = withContext(coroutineDispatcher) {
        preferencesRepository.getClient(PREFS_KEY)
    }

    override suspend fun deleteClient() {
        preferencesRepository.deleteClient(PREFS_KEY)
    }

    companion object {
        const val PREFS_KEY = "TradingAppKey"
    }
}
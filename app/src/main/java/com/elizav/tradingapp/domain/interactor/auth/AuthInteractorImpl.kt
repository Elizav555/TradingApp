package com.elizav.tradingapp.domain.interactor.auth

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.interactor.preferences.PreferencesInteractor
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthInteractorImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val preferencesInteractor: PreferencesInteractor
) : AuthInteractor {
    override suspend fun auth(login: String, password: String): Result<Client> =
        withContext(coroutineDispatcher) {
            authRepository.peanutAuth(login, password).fold(
                onSuccess = { peanutToken ->
                    authRepository.partnerAuth(login, password).fold(
                        onSuccess = { partnerToken ->
                            val client = Client(
                                login = login,
                                peanutToken = peanutToken,
                                partnerToken = partnerToken
                            )
                            preferencesInteractor.saveClient(client)
                            Result.success(
                                client
                            )
                        },
                        onFailure = { ex -> Result.failure(ex) }
                    )
                },
                onFailure = { ex -> Result.failure(ex) }
            )
        }

    override suspend fun logout(): Result<Boolean> {
        return try {
            preferencesInteractor.deleteClient()
            Result.success(true)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    override suspend fun checkAuth(): Result<Client?> {
        return try {
            Result.success(preferencesInteractor.getClient())
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }
}
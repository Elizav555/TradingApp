package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.model.Client
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthInteractorImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) :
    AuthInteractor {
    override suspend fun auth(login: String, password: String): Result<Client> =
        withContext(coroutineDispatcher) {
            authRepository.peanutAuth(login, password).fold(
                onSuccess = { peanutToken ->
                    authRepository.partnerAuth(login, password).fold(
                        onSuccess = { partnerToken ->
                            Result.success(
                                Client(
                                    login = login,
                                    peanutToken = peanutToken,
                                    partnerToken = partnerToken
                                )
                            )
                        },
                        onFailure = { ex -> Result.failure(ex) }
                    )
                },
                onFailure = { ex -> Result.failure(ex) }
            )
        }

    override suspend fun logout(): Result<Boolean> = withContext(coroutineDispatcher) {
        authRepository.logout()
    }
}
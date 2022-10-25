package com.elizav.tradingapp.domain.interactor.auth

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

class AuthInteractorImpl @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : AuthInteractor {
    override fun isClientAuthed(): SharedFlow<Boolean> = authRepository.isClientAuthed()
    override suspend fun auth(login: String, password: String): Result<Client> =
        withContext(coroutineDispatcher) {
            authRepository.auth(login, password)
        }

    override suspend fun logout(): Result<Boolean> = withContext(coroutineDispatcher) {
        authRepository.logout()
    }

    override suspend fun checkAuth(): Result<Client?> = withContext(coroutineDispatcher) {
        authRepository.checkAuth()
    }
}
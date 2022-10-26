package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.model.params.AuthParams
import com.elizav.tradingapp.data.network.api.PeanutApi
import com.elizav.tradingapp.data.network.requests.PartnerRequest
import com.elizav.tradingapp.domain.interactor.preferences.PreferencesInteractor
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.utils.AppException
import com.elizav.tradingapp.domain.utils.AppException.Companion.AUTH_EXCEPTION
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AuthRepositoryImpl @Inject constructor(
    private val peanutApi: PeanutApi,
    private val partnerRequest: PartnerRequest,
    private val preferencesInteractor: PreferencesInteractor
) : AuthRepository {
    private val _isClientAuthed =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun isClientAuthed(): SharedFlow<Boolean> = _isClientAuthed

    override suspend fun peanutAuth(login: String, password: String): Result<String> =
        try {
            with(peanutApi.isAccountCredentialsCorrect(AuthParams(login, password))) {
                if (isSuccessful && body() != null) {
                    Result.success(body()!!.token)
                } else {
                    Result.failure(
                        AppException.AuthException(
                            errorBody()?.string() ?: AUTH_EXCEPTION
                        )
                    )
                }
            }
        } catch (ex: Throwable) {
            if (ex is UnknownHostException|| ex is SocketTimeoutException) {
                Result.failure(AppException.InternetException())
            } else Result.failure(
                AppException.AuthException(
                    ex.message ?: AUTH_EXCEPTION
                )
            )
        }

    override suspend fun partnerAuth(login: String, password: String): Result<String> =
        try {
            partnerRequest(
                AuthParams(login, password)
            )
        } catch (ex: Throwable) {
            if (ex is UnknownHostException|| ex is SocketTimeoutException) {
                Result.failure(AppException.InternetException())
            } else Result.failure(
                AppException.AuthException(
                    ex.message ?: AUTH_EXCEPTION
                )
            )
        }

    override suspend fun auth(login: String, password: String): Result<Client> =
        peanutAuth(login, password).fold(
            onSuccess = { peanutToken ->
                partnerAuth(login, password).fold(
                    onSuccess = { partnerToken ->
                        val client = Client(
                            login = login,
                            peanutToken = peanutToken,
                            partnerToken = partnerToken
                        )
                        preferencesInteractor.saveClient(client)
                        _isClientAuthed.tryEmit(true)
                        Result.success(
                            client
                        )
                    },
                    onFailure = { ex -> Result.failure(ex) }
                )
            },
            onFailure = { ex -> Result.failure(ex) }
        )

    override suspend fun logout(): Result<Boolean> {
        return try {
            preferencesInteractor.deleteClient()
            _isClientAuthed.tryEmit(false)
            Result.success(true)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    override suspend fun checkAuth(): Result<Client?> {
        return try {
            val client = preferencesInteractor.getClient()
            val isAuthed = client?.partnerToken != null && client.peanutToken != null
            if (isAuthed) {
                Result.success(client)
            } else Result.success(null)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    override suspend fun invalidateTokens() {
        logout()
        _isClientAuthed.tryEmit(false)
    }
}
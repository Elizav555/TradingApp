package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.model.params.AuthParams
import com.elizav.tradingapp.data.network.requests.PartnerRequest
import com.elizav.tradingapp.data.network.api.PeanutApi
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.model.AppException
import com.elizav.tradingapp.domain.model.AppException.Companion.AUTH_EXCEPTION
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val peanutApi: PeanutApi,
    private val partnerRequest: PartnerRequest
) : AuthRepository {
    override suspend fun peanutAuth(login: String, password: String): Result<String> =
        with(peanutApi.isAccountCredentialsCorrect(AuthParams(login, password))) {
            if (isSuccessful && body() != null) {
                Result.success(body()!!.token)
            } else {
                Result.failure(AppException.AuthException(errorBody()?.string() ?: AUTH_EXCEPTION))
            }
        }

    override suspend fun partnerAuth(login: String, password: String): Result<String> =
        partnerRequest(
            AuthParams(login, password)
        )

    override suspend fun logout(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}
package com.elizav.tradingapp.data

import com.elizav.tradingapp.data.model.AuthParams
import com.elizav.tradingapp.data.network.PartnerRequest
import com.elizav.tradingapp.data.network.PeanutApi
import com.elizav.tradingapp.domain.AuthRepository
import com.elizav.tradingapp.domain.model.AppException
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
                Result.failure(errorBody()?.string()?.let { AppException.ApiException(it) }
                    ?: AppException.ApiException())
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
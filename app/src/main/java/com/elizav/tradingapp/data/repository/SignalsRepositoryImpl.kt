package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.SignalMapper.toDomain
import com.elizav.tradingapp.data.network.CODE_FORBIDDEN
import com.elizav.tradingapp.data.network.api.PartnerApi
import com.elizav.tradingapp.domain.model.signal.Signal
import com.elizav.tradingapp.domain.model.utils.AppException
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.repository.SignalsRepository
import javax.inject.Inject

class SignalsRepositoryImpl @Inject constructor(
    private val partnerApi: PartnerApi,
    private val authRepository: AuthRepository
) :
    SignalsRepository {
    override suspend fun getSignals(
        login: String,
        partnerToken: String,
        pairs: String,
        from: Long,
        to: Long
    ): Result<List<Signal>> = with(
        partnerApi.getAnalyticSignals(
            partnerToken = partnerToken, login = login, pairs = pairs, from = from, to = to
        )
    ) {
        if (isSuccessful && body() != null) {
            Result.success(body()!!.map { it.toDomain() })
        } else {
            if (code() == CODE_FORBIDDEN) {
                authRepository.invalidateTokens()
            }
            Result.failure(AppException.ApiException())
        }
    }
}
package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.AccountInfoMapper.toDomain
import com.elizav.tradingapp.data.model.params.InfoParams
import com.elizav.tradingapp.data.network.CODE_FORBIDDEN
import com.elizav.tradingapp.data.network.CODE_SERVER_ERROR
import com.elizav.tradingapp.data.network.api.PeanutApi
import com.elizav.tradingapp.domain.model.client.AccountInfo
import com.elizav.tradingapp.domain.utils.AppException
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.repository.ClientInfoRepository
import javax.inject.Inject

class ClientInfoRepositoryImpl @Inject constructor(
    private val peanutApi: PeanutApi,
    private val authRepository: AuthRepository
) : ClientInfoRepository {
    override suspend fun getLastFourNumbersPhone(
        login: String,
        peanutToken: String
    ): Result<String> = with(
        peanutApi.getLastFourNumbersPhone(
            InfoParams(
                login = login,
                token = peanutToken
            )
        )
    ) {
        if (isSuccessful && body() != null) {
            Result.success(body()!!)
        } else {
            if (code() == CODE_FORBIDDEN || code() == CODE_SERVER_ERROR) {
                authRepository.invalidateTokens()
            }
            Result.failure(
                AppException.ApiException(
                    errorBody()?.string() ?: AppException.API_EXCEPTION
                )
            )
        }
    }


    override suspend fun getAccountInfo(login: String, peanutToken: String): Result<AccountInfo> =
        with(
            peanutApi.getAccountInformation(
                InfoParams(
                    login = login,
                    token = peanutToken
                )
            )
        ) {
            if (isSuccessful && body() != null) {
                Result.success(body()!!.toDomain())
            } else {
                if (code() == CODE_FORBIDDEN || code() == CODE_SERVER_ERROR) {
                    authRepository.invalidateTokens()
                }
                Result.failure(
                    AppException.ApiException(
                        errorBody()?.string() ?: AppException.API_EXCEPTION
                    )
                )
            }
        }

}
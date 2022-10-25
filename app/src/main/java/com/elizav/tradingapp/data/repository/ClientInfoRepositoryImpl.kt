package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.AccountInfoMapper.toDomain
import com.elizav.tradingapp.data.model.params.InfoParams
import com.elizav.tradingapp.data.network.api.PeanutApi
import com.elizav.tradingapp.domain.repository.ClientInfoRepository
import com.elizav.tradingapp.domain.model.client.AccountInfo
import com.elizav.tradingapp.domain.model.utils.AppException
import javax.inject.Inject

class ClientInfoRepositoryImpl @Inject constructor(
    private val peanutApi: PeanutApi,
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
                Result.failure(
                    AppException.ApiException(
                        errorBody()?.string() ?: AppException.API_EXCEPTION
                    )
                )
            }
        }

}
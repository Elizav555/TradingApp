package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.AccountInfoMapper.toDomain
import com.elizav.tradingapp.data.model.params.InfoParams
import com.elizav.tradingapp.data.network.CODE_FORBIDDEN
import com.elizav.tradingapp.data.network.CODE_SERVER_ERROR
import com.elizav.tradingapp.data.network.api.PeanutApi
import com.elizav.tradingapp.domain.model.client.AccountInfo
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.repository.ClientInfoRepository
import com.elizav.tradingapp.domain.utils.AppException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ClientInfoRepositoryImpl @Inject constructor(
    private val peanutApi: PeanutApi,
    private val authRepository: AuthRepository
) : ClientInfoRepository {
    override suspend fun getLastFourNumbersPhone(
        login: String,
        peanutToken: String
    ): Result<String> = try {
        with(
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
    } catch (ex: Throwable) {
        if (ex is UnknownHostException|| ex is SocketTimeoutException) {
            Result.failure(AppException.InternetException())
        } else Result.failure(
            AppException.AuthException(
                ex.message ?: AppException.AUTH_EXCEPTION
            )
        )
    }

    override suspend fun getAccountInfo(login: String, peanutToken: String): Result<AccountInfo> =
        try {
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
        } catch (ex: Throwable) {
            if (ex is UnknownHostException|| ex is SocketTimeoutException) {
                Result.failure(AppException.InternetException())
            } else Result.failure(
                AppException.AuthException(
                    ex.message ?: AppException.AUTH_EXCEPTION
                )
            )
        }
}
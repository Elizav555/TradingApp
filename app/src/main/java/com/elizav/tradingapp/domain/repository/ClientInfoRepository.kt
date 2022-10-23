package com.elizav.tradingapp.domain.repository

import com.elizav.tradingapp.domain.model.AccountInfo

interface ClientInfoRepository {
    suspend fun getLastFourNumbersPhone(login: String, peanutToken: String): Result<String>
    suspend fun getAccountInfo(login: String, peanutToken: String): Result<AccountInfo>
}
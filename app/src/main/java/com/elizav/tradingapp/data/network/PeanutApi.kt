package com.elizav.tradingapp.data.network

import com.elizav.tradingapp.data.model.AccountInfo
import com.elizav.tradingapp.data.model.params.AuthParams
import com.elizav.tradingapp.data.model.params.InfoParams
import com.elizav.tradingapp.data.model.token.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PeanutApi {
    @POST("/api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    suspend fun isAccountCredentialsCorrect(
        @Body authParams: AuthParams
    ): Response<Token>

    @POST("/api/ClientCabinetBasic/GetLastFourNumbersPhone")
    suspend fun getLastFourNumbersPhone(
        @Body infoParams: InfoParams
    ): Response<String>

    @POST("/api/ClientCabinetBasic/GetAccountInformation")
    suspend fun getAccountInformation(
        @Body infoParams: InfoParams
    ): Response<AccountInfo>
}
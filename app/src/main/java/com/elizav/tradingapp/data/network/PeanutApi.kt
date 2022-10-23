package com.elizav.tradingapp.data.network

import com.elizav.tradingapp.data.model.AuthParams
import com.elizav.tradingapp.data.model.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PeanutApi {
    @POST("/api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    suspend fun isAccountCredentialsCorrect(
        @Body authParams: AuthParams
    ): Response<Token>
}
package com.elizav.tradingapp.data.network

import com.elizav.tradingapp.data.model.Signal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PartnerApi {
    @GET("/clientmobile/GetAnalyticSignals/{login}?")
    suspend fun getAnalyticSignals(
        @Header("passkey") partnerToken: String,
        @Path("login") login: String,
        @Query("tradingsystem") tradingSystem: Int = 3,
        @Query("pairs") pairs: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): Response<List<Signal>>
}
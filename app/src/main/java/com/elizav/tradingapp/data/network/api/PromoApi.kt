package com.elizav.tradingapp.data.network.api

import com.elizav.tradingapp.data.model.promo.GetCCPromo
import com.elizav.tradingapp.data.model.promo.PromoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PromoApi {
    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @POST("/Services/CabinetMicroService.svc")
    fun requestPromo(@Body body: GetCCPromo): Response<PromoResponse>
}
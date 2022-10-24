package com.elizav.tradingapp.data

import com.elizav.tradingapp.data.model.promo.GetCCPromo
import com.elizav.tradingapp.data.network.api.PromoApi
import com.elizav.tradingapp.domain.model.AppException
import com.elizav.tradingapp.domain.repository.PromoRepository
import javax.inject.Inject

class PromoRepositoryImpl @Inject constructor(private val promoApi: PromoApi) : PromoRepository {
    override suspend fun getPromo(lang: String): Result<String> {
        val requestPromo = GetCCPromo()
        requestPromo.setLang("en")
        return with(promoApi.requestPromo(requestPromo)) {
            if (isSuccessful && body() != null) {
                Result.success(body()!!.GetCCPromoResult)
            } else {
                Result.failure(AppException.ApiException())
            }
        }
    }
}
package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.PromoMapper.toDomain
import com.elizav.tradingapp.data.network.requests.PromoRequest
import com.elizav.tradingapp.domain.model.Promo
import com.elizav.tradingapp.domain.repository.PromoRepository
import javax.inject.Inject

class PromoRepositoryImpl @Inject constructor(private val promoRequest: PromoRequest) :
    PromoRepository {
    override suspend fun getPromo(lang: String): Result<List<Promo>> {
        return promoRequest(lang).fold(
            onSuccess = { promoMap ->
                Result.success(
                    promoMap.map { map -> map.value.toDomain(map.key) }
                )
            },
            onFailure = { Result.failure(it) }
        )
    }
}

package com.elizav.tradingapp.data.repository

import com.elizav.tradingapp.data.mapper.PromoMapper.toDomain
import com.elizav.tradingapp.data.network.requests.PromoRequest
import com.elizav.tradingapp.domain.model.Promo
import com.elizav.tradingapp.domain.repository.PromoRepository
import com.elizav.tradingapp.domain.utils.AppException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class PromoRepositoryImpl @Inject constructor(private val promoRequest: PromoRequest) :
    PromoRepository {
    override suspend fun getPromo(lang: String): Result<List<Promo>> {
        return try {
            promoRequest(lang).fold(
                onSuccess = { promoMap ->
                    Result.success(
                        promoMap.map { map -> map.value.toDomain(map.key) }
                    )
                },
                onFailure = { Result.failure(it) }
            )
        } catch (ex: Throwable) {
            if (ex is UnknownHostException || ex is SocketTimeoutException) {
                Result.failure(AppException.InternetException())
            } else Result.failure(
                AppException.AuthException(
                    ex.message ?: AppException.AUTH_EXCEPTION
                )
            )
        }
    }
}

package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.repository.PromoRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PromoInteractorImpl @Inject constructor(
    private val promoRepository: PromoRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : PromoInteractor {
    override suspend fun getPromo(lang: String): Result<String> = withContext(coroutineDispatcher) {
        promoRepository.getPromo(lang)
    }
}
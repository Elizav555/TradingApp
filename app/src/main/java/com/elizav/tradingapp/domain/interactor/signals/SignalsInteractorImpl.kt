package com.elizav.tradingapp.domain.interactor.signals

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.model.signal.SignalPair
import com.elizav.tradingapp.domain.model.signal.Signal
import com.elizav.tradingapp.domain.repository.SignalsRepository
import com.elizav.tradingapp.domain.utils.AppException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SignalsInteractorImpl @Inject constructor(
    private val signalsRepository: SignalsRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SignalsInteractor {
    override suspend fun getSignals(
        client: Client,
        signalPairs: List<SignalPair>,
        from: Long,
        to: Long
    ): Result<List<Signal>> = withContext(coroutineDispatcher) {
        if (client.partnerToken == null) {
            return@withContext Result.failure(AppException.ClientStateException())
        }
        signalsRepository.getSignals(
            login = client.login,
            partnerToken = client.partnerToken,
            pairs = signalPairs.joinToString(",", transform = { pair -> pair.name }),
            from = from,
            to = to
        )
    }
}
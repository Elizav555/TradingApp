package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.di.qualifiers.IoDispatcher
import com.elizav.tradingapp.domain.model.AppException
import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.domain.model.Pair
import com.elizav.tradingapp.domain.model.Signal
import com.elizav.tradingapp.domain.repository.SignalsRepository
import java.util.Calendar
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// tradingsystem=3&pairs=GBPJPY,EURJPY&from=1479860023&to=1480066860
class SignalsInteractorImpl @Inject constructor(
    private val signalsRepository: SignalsRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SignalsInteractor {
    override suspend fun getSignals(
        client: Client,
        pairs: List<Pair>,
        from: Calendar,
        to: Calendar
    ): Result<List<Signal>> = withContext(coroutineDispatcher) {
        if (client.partnerToken == null) {
            return@withContext Result.failure(AppException.ClientStateException())
        }
        signalsRepository.getSignals(
            login = client.login,
            partnerToken = client.partnerToken,
            pairs = pairs.joinToString(",", transform = { pair -> pair.name }),
            from = from.timeInMillis,
            to = to.timeInMillis
        )
    }
}
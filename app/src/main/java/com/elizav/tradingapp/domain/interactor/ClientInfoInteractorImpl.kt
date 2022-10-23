package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.di.coroutine.IoDispatcher
import com.elizav.tradingapp.domain.ClientInfoRepository
import com.elizav.tradingapp.domain.model.AppException
import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.domain.model.ClientInfo
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ClientInfoInteractorImpl @Inject constructor(
    private val clientInfoRepository: ClientInfoRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ClientInfoInteractor {
    override suspend fun getClientInfo(client: Client): Result<ClientInfo> =
        withContext(coroutineDispatcher) {
            if (client.peanutToken == null) {
                return@withContext Result.failure(AppException.ClientStateException())
            }
            clientInfoRepository.getAccountInfo(client.login, client.peanutToken).fold(
                onSuccess = { accountInfo ->
                    clientInfoRepository.getLastFourNumbersPhone(client.login, client.peanutToken)
                        .fold(
                            onSuccess = { lastFourNumb ->
                                Result.success(
                                    ClientInfo(
                                        lastFourNumbPhone = lastFourNumb, accountInfo = accountInfo
                                    )
                                )
                            },
                            onFailure = { ex -> Result.failure(ex) }
                        )
                },
                onFailure = { ex -> Result.failure(ex) }
            )
        }
}
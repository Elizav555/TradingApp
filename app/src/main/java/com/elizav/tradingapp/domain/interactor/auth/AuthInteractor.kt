package com.elizav.tradingapp.domain.interactor.auth

import com.elizav.tradingapp.domain.model.client.Client

interface AuthInteractor {
    suspend fun auth(login: String, password: String): Result<Client>
    suspend fun logout(): Result<Boolean>
    suspend fun checkAuth(): Result<Client?>
}
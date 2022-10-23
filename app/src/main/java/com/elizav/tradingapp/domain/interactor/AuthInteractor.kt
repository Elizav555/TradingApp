package com.elizav.tradingapp.domain.interactor

import com.elizav.tradingapp.domain.model.Client

interface AuthInteractor {
    suspend fun auth(login: String, password: String): Result<Client>
    suspend fun logout(): Result<Boolean>
}
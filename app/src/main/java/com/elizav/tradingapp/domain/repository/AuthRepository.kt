package com.elizav.tradingapp.domain.repository

import com.elizav.tradingapp.domain.model.client.Client
import kotlinx.coroutines.flow.SharedFlow

interface AuthRepository {
    fun isClientAuthed(): SharedFlow<Boolean>
    suspend fun auth(login: String, password: String): Result<Client>
    suspend fun peanutAuth(login: String, password: String): Result<String>
    suspend fun partnerAuth(login: String, password: String): Result<String>
    suspend fun logout(): Result<Boolean>
    suspend fun checkAuth(): Result<Client?>
    suspend fun invalidateTokens()
}
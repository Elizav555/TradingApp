package com.elizav.tradingapp.domain

interface AuthRepository {
    suspend fun peanutAuth(login: String, password: String): Result<String>
    suspend fun partnerAuth(login: String, password: String): Result<String>
    suspend fun logout(): Result<Boolean>
}
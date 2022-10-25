package com.elizav.tradingapp.domain.utils

sealed class Command {
    data class ErrorCommand(val errorMessage: String) : Command()
}
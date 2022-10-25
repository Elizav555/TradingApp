package com.elizav.tradingapp.domain.model.utils

sealed class Command {
    data class ErrorCommand(val errorMessage: String) : Command()
}
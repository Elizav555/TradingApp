package com.elizav.tradingapp.ui.utils

sealed class Command {
    data class ErrorCommand(val errorMessage: String) : Command()
    data class HandleLoadingCommand(val isLoading: Boolean) : Command()
}
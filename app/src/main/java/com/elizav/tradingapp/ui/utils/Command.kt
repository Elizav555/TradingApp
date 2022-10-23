package com.elizav.tradingapp.ui.utils

sealed class Command {
    data class ErrorCommand(val errorMessage: String) : Command()
}
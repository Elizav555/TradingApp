package com.elizav.tradingapp.domain.model.signal

import java.util.Calendar

data class Signal(
    val actualTime: Calendar,
    val cmd: Int,
    val comment: String,
    val id: Int,
    val signalPair: SignalPair?,
    val period: String,
    val price: Double,
    val sl: Double,
    val tp: Double,
    val tradingSystem: Int
)
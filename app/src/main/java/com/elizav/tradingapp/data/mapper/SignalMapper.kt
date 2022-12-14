package com.elizav.tradingapp.data.mapper

import com.elizav.tradingapp.domain.model.signal.Signal
import java.util.Calendar
import com.elizav.tradingapp.data.model.Signal as SignalData

object SignalMapper {
    fun SignalData.toDomain() = Signal(
        actualTime = Calendar.getInstance().apply {
            timeInMillis = ActualTime
        },
        cmd = Cmd,
        comment = Comment,
        id = Id,
        signalPair = com.elizav.tradingapp.domain.model.signal.SignalPair.values().firstOrNull { it.name == Pair },
        period = Period,
        price = Price,
        sl = Sl,
        tp = Tp,
        tradingSystem = TradingSystem
    )

    fun Signal.toData() = SignalData(
        ActualTime = actualTime.timeInMillis,
        Cmd = cmd,
        Comment = comment,
        Id = id,
        Pair = signalPair?.name ?: "",
        Period = period,
        Price = price,
        Sl = sl,
        Tp = tp,
        TradingSystem = tradingSystem
    )
}
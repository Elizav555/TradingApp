package com.elizav.tradingapp.domain.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object DateParser {
    private val dateFormatDefault = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    fun getTimeInUnixDateFromString(
        dateString: String,
        dateFormat: SimpleDateFormat = dateFormatDefault
    ): Long? = try {
        dateFormat.parse(dateString)?.time?.div(1000)
    } catch (ex: ParseException) {
        null
    }
}
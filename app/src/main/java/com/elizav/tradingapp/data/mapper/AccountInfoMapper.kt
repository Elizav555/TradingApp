package com.elizav.tradingapp.data.mapper

import com.elizav.tradingapp.domain.model.client.AccountInfo
import com.elizav.tradingapp.data.model.client.AccountInfo as AccountInfoData

object AccountInfoMapper {
    fun AccountInfoData.toDomain()= AccountInfo(
        address = address,
        balance = balance,
        city = city,
        country = country,
        currency = currency,
        currentTradesCount = currentTradesCount,
        currentTradesVolume = currentTradesVolume,
        equity = equity,
        freeMargin = freeMargin,
        isAnyOpenTrades = isAnyOpenTrades,
        isSwapFree = isSwapFree,
        leverage = leverage,
        name = name,
        phone = phone,
        totalTradesCount = totalTradesCount,
        totalTradesVolume = totalTradesVolume,
        type = type,
        verificationLevel = verificationLevel,
        zipCode = zipCode
    )

    fun AccountInfo.toData()=AccountInfoData(
        address = address,
        balance = balance,
        city = city,
        country = country,
        currency = currency,
        currentTradesCount = currentTradesCount,
        currentTradesVolume = currentTradesVolume,
        equity = equity,
        freeMargin = freeMargin,
        isAnyOpenTrades = isAnyOpenTrades,
        isSwapFree = isSwapFree,
        leverage = leverage,
        name = name,
        phone = phone,
        totalTradesCount = totalTradesCount,
        totalTradesVolume = totalTradesVolume,
        type = type,
        verificationLevel = verificationLevel,
        zipCode = zipCode,
        extensionData = null
    )
}
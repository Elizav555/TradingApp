package com.elizav.tradingapp.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PeanutBaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PartnerBaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PromoBaseUrl
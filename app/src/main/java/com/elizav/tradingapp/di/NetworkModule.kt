package com.elizav.tradingapp.di

import com.elizav.tradingapp.data.network.BaseUrl
import com.elizav.tradingapp.data.network.api.PartnerApi
import com.elizav.tradingapp.data.network.api.PeanutApi
import com.elizav.tradingapp.di.qualifiers.PartnerBaseUrl
import com.elizav.tradingapp.di.qualifiers.PeanutBaseUrl
import com.elizav.tradingapp.di.qualifiers.PromoBaseUrl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    fun provideGsonConvertFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setLenient().create())

    @Provides
    @Singleton
    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
        )
        .cache(null)
        .build()

    @Provides
    @Singleton
    @PeanutBaseUrl
    fun providePeanutBaseUrl(): BaseUrl = BaseUrl.PeanutBaseUrl()

    @Provides
    @Singleton
    @PartnerBaseUrl
    fun providePartnerBaseUrl(): BaseUrl = BaseUrl.PartnerBaseUrl()

    @Provides
    @Singleton
    @PromoBaseUrl
    fun providePromoBaseUrl(): BaseUrl = BaseUrl.PromoBaseUrl()

    @Provides
    @Singleton
    fun providePeanutApi(
        @PeanutBaseUrl baseUrl: BaseUrl,
        okhttp: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): PeanutApi = Retrofit.Builder()
        .baseUrl(baseUrl.value)
        .client(okhttp)
        .addConverterFactory(converterFactory)
        .build()
        .create(PeanutApi::class.java)

    @Provides
    @Singleton
    fun providePartnerApi(
        @PartnerBaseUrl baseUrl: BaseUrl,
        okhttp: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): PartnerApi = Retrofit.Builder()
        .baseUrl(baseUrl.value)
        .client(okhttp)
        .addConverterFactory(converterFactory)
        .build()
        .create(PartnerApi::class.java)
}

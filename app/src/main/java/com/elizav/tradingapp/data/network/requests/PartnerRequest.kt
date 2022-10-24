package com.elizav.tradingapp.data.network.requests

import com.elizav.tradingapp.data.model.params.AuthParams
import com.elizav.tradingapp.domain.model.AppException
import javax.inject.Inject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.converter.gson.GsonConverterFactory

class PartnerRequest @Inject constructor(
    private val okHttpClient: OkHttpClient,
) {
    operator fun invoke(authParams: AuthParams): Result<String> {
        val body: RequestBody = Json.encodeToString(authParams)
            .toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("https://client-api.contentdatapro.com/api/Authentication/RequestMoblieCabinetApiToken")
            .post(body)
            .build()

        return okHttpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful && response.body != null) {
                Result.success(Json.decodeFromString<String>(response.body!!.string()))
            } else {
                Result.failure(AppException.ApiException(response.message))
            }
        }
    }
}
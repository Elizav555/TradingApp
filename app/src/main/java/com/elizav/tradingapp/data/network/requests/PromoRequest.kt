package com.elizav.tradingapp.data.network.requests

import com.elizav.tradingapp.data.model.promo.Promo
import com.elizav.tradingapp.data.model.promo.PromoConverter
import com.elizav.tradingapp.domain.model.AppException
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class PromoRequest @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val promoConverter: PromoConverter
) {
    operator fun invoke(lang: String): Result<Map<String, Promo>> {
        val bodyString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetCCPromo xmlns=\"http://tempuri.org/\">\n" +
                "      <lang>${lang}</lang>\n" +
                "    </GetCCPromo>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>".trimIndent()
        val body = bodyString.toRequestBody("text/xml".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("https://api-forexcopy.contentdatapro.com/Services/CabinetMicroService.svc")
            .addHeader("Content-Type", "text/xml; charset=utf-8")
            .addHeader("SOAPAction", "http://tempuri.org/CabinetMicroService/GetCCPromo")
            .post(body)
            .build()

        return okHttpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful && response.body != null) {
                val promoMap = promoConverter(response.body!!.string())
                Result.success(promoMap)
            } else {
                Result.failure(AppException.ApiException(response.message))
            }
        }
    }
}
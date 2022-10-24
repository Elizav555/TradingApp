package com.elizav.tradingapp.domain.model

class EncodedLink(
    private val originalLink: String
) {
    val encodedLink: String get() = originalLink
        .replace("/", "!$")
        .replace("&","!*")
        .replace("?","!!")
    companion object{
        fun decodeLink(link:String)=link
            .replace("!$", "/")
            .replace("!*","&")
            .replace("!!","?")
    }
}

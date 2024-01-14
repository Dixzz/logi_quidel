package com.loginext.quidel.models.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeComponentResponse(
    val `data`: Data,
    val status: Int
)
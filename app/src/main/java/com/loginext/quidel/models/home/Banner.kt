package com.loginext.quidel.models.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Banner(
    val image_url: String
)
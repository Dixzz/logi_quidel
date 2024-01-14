package com.loginext.quidel.models.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Restaurant(
    val additional_offer: String = "",
    val display_distance: String,
    val image_url: String,
    val name: String,
    val offers: List<Offer>,
    val rating: Double,
)
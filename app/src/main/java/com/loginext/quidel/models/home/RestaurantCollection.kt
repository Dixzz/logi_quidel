package com.loginext.quidel.models.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantCollection(
    val name: String,
    val priority: Int,
    val restaurants: List<Restaurant>
)
package com.loginext.quidel.models.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodCategory(
    val icon: String,
//    val id: Int,
    val name: String
)
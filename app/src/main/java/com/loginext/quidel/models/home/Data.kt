package com.loginext.quidel.models.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val banners: List<Banner>,
    val food_categories: List<FoodCategory>,
    val number_of_active_vouchers: Int,
    val offer_collections: List<OfferCollection>,
    var restaurant_collections: List<RestaurantCollection>
)
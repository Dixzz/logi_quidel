package com.loginext.quidel.models.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfferCollection(
//    val background: String,
    val image: String,
    val name: String,
//    val textcolor: String
){

}
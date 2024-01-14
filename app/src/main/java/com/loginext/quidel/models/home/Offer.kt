package com.loginext.quidel.models.home

import android.graphics.Color
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class HexColor

@JsonClass(generateAdapter = true)
data class Offer(
    val name: String,// deal $1
    @HexColor val background: Int = Color.WHITE, //hex e.g: FF00FF
    @HexColor val textcolor: Int = Color.BLACK, //hex
) {

    class ColorJsonAdapter {
        @ToJson
        fun toJson(@HexColor rgb: Int): String {
            return "#%06x".format(rgb)
        }

        @FromJson
        @HexColor
        fun fromJson(rgb: String): Int {
            return Color.parseColor("#$rgb")
        }
    }
}
package com.loginext.quidel.models

import androidx.annotation.Keep

@Keep
open class CommonObject(
    val message: String = "Something went wrong",
    val status: Boolean = false
)

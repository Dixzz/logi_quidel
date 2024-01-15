package com.loginext.quidel.helpers

import androidx.annotation.VisibleForTesting
import androidx.test.espresso.idling.CountingIdlingResource

@VisibleForTesting
object CountingIdlingResourceHelper {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {

        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}
package com.loginext.quidel.models

import androidx.annotation.Keep


@Keep
data class AutoResolver<T>(
    val status: ApiResult,
    val data: T? = null,
    val message: String? = null,
) {
    companion object {

        sealed class ApiResult

        object Loading : ApiResult()
        object Success : ApiResult()
        object Failure : ApiResult()

        fun <T> success(data: T): AutoResolver<T> {
            return AutoResolver(Success, data, null)
        }

        fun <T> failure(msg: String? = null): AutoResolver<T> {
            return AutoResolver(Failure, null, msg ?: "Something went wrong")
        }

        fun <T> loading(): AutoResolver<T> {
            return AutoResolver(Loading)
        }

    }
}
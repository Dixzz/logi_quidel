package com.loginext.quidel.repository.home

import com.loginext.quidel.api.ApiService
import javax.inject.Inject

class HomeRepo @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun homeScreenComponents() = apiService.homeScreenComponents()
}
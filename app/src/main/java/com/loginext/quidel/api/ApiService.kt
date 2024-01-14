package com.loginext.quidel.api

import com.loginext.quidel.models.home.HomeComponentResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("0c5d380f-972a-44c9-bd11-ca5a2f154019")
    suspend fun homeScreenComponents(): HomeComponentResponse
}
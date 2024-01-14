package com.loginext.quidel.di

import androidx.annotation.Keep
import com.loginext.quidel.BuildConfig
import com.loginext.quidel.api.ApiService
import com.loginext.quidel.helpers.UrlConstants
import com.loginext.quidel.models.home.Offer
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Provides required instances as dependencies to other
 * classes and maintains singleton behaviour.
 * Specifies how to provide instances of different types.
 * Include bindings for types that cannot be constructor-injected such as
 * interfaces or classes that are not contained in your project.
 * An example of this is OkHttpClient - you need to use its builder to create an instance.
 */

// TODO: Rename variables
@Module
@InstallIn(SingletonComponent::class)  // tell Hilt which Android class this module will be used
// or installed in, SingletonComponent for Application level dependencies
@Keep
object AppModule {

    @Provides
    fun provideMoshi() = run {
        Moshi.Builder().apply {
            add(Offer.ColorJsonAdapter())
        }.build()
    }

    @Provides
    fun provideRetrofit(
        moshi: Moshi,
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build()
        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(UrlConstants.URL)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
package com.loginext.quidel.di

import android.view.LayoutInflater
import androidx.annotation.Keep
import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
@Keep
object ActModule {


    @Provides
    fun provideInflator(context: FragmentActivity) = run {
        LayoutInflater.from(context)
    }

}
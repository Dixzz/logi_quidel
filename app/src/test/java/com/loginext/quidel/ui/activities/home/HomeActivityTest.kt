package com.loginext.quidel.ui.activities.home

import android.os.Build
import androidx.test.core.app.ActivityScenario
import com.loginext.quidel.models.AutoResolver
import com.loginext.quidel.repository.home.HomeRepo
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var homeRepo: HomeRepo

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `home page components GET`(): Unit = runBlocking {
        assertEquals(200, homeRepo.homeScreenComponents().status)
    }
}
package com.loginext.quidel.ui.activities.home

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.loginext.quidel.helpers.CountingIdlingResourceHelper
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.ext.junit.rules.activityScenarioRule
import com.loginext.quidel.databinding.ActivityHomeBinding
import org.junit.Rule

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    private lateinit var homeActivity: HomeActivity
    @get:Rule
    var activityScenarioRule = activityScenarioRule<HomeActivity>()

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(CountingIdlingResourceHelper.countingIdlingResource)
        activityScenarioRule.scenario.onActivity {
            homeActivity = it
        }
    }

    /**
     * Check if API is hit and [ActivityHomeBinding.mainRoot] is visible
     */
    @Test
    fun apiComponentLoaded() {
        onView(withId(homeActivity.binding.mainRoot.id)).check(ViewAssertions.matches(isDisplayed()))
    }

    @After
    fun destroy() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceHelper.countingIdlingResource)
    }
}
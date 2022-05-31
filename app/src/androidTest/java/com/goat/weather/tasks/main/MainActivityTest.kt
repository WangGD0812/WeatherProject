package com.goat.weather.tasks.main

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNewYorkLocation() {
        activityRule.scenario.onActivity {
            it.mPresenter?.latitude = 40.72975728760404
            it.mPresenter?.longitude = -73.99623559658782
            it.mPresenter?.requestWeatherData(it)
        }
    }

}
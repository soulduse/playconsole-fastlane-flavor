package com.dave.fastlanetest

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.cleanstatusbar.CleanStatusBar
import tools.fastlane.screengrab.locale.LocaleTestRule
import java.time.Duration

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val localeTestRule = LocaleTestRule()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        onView(ViewMatchers.isRoot()).perform(waitFor(Duration.ofSeconds(1)))
        Screengrab.screenshot("1")
        onView(withId(R.id.btn_next)).perform(click())
        Screengrab.screenshot("2")
    }

    private fun waitFor(duration: Duration): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for ${duration.toMillis()} milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(duration.toMillis())
            }
        }
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
            CleanStatusBar.enableWithDefaults()
        }

        @AfterClass
        @JvmStatic
        fun afterAll() {
            CleanStatusBar.disable()
        }
    }
}

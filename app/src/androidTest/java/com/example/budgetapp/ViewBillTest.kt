package com.example.budgetapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.budgetapp.activities.FetchingBill
import com.example.budgetapp.activities.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewBillTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.appsquad", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var activityScenario: ActivityScenario<MainActivity>
    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
        }
    }

    @Before
    fun setUp() {
        activityScenario = activityRule.activity
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
        }
    }

    @Test
    fun testButton(){


        Intents.init()
        Espresso.onView(ViewMatchers.withId(R.id.btnViewBill))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btnViewBill)).perform(ViewActions.click());


        Intents.intended(IntentMatchers.hasComponent(FetchingBill::class.java.name))
    }
    @After
    fun tearDown() {
        activityScenario.close()
        }
}
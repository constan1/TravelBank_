package com.exercise.travelbank_.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.exercise.travelbank_.R
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.exercise.travelbank_.bindingadapters.adapters.ExpensesAdapter
import com.exercise.travelbank_.models.ExpensesDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ExpensesFragmentTest : TestCase() {

    @ExperimentalCoroutinesApi
    @get: Rule
    val activityScenerio = ActivityScenarioRule(MainActivity::class.java)


    private val lastItemInTest = 3
    private val expenseInTest = ExpensesDTO(
        1.82, null, "2021-07-09T00:00:00.000Z",
        "", "Coffee", "perDiem", "EUR"
    )


    @Test
    fun expenseFragmentLaunch_expensesFragment_isVisibleOnLaunch() {
        onView(withId(R.id.expenses_list)).check(matches(isDisplayed()))
    }


    @Test
    fun expenseDetailsFragmentLaunch_selectedItemToDetailsFragment_isDetailsFragmentVisible() {

        onView(withId(R.id.expenses_list))
            .perform(
                actionOnItemAtPosition<ExpensesAdapter.ExpensesHolder>(
                    lastItemInTest,
                    click()
                )
            )

        onView(withId(R.id.editTextMerchantTitle)).check(matches(withText(expenseInTest.expenseVenueTitle)))
    }

    @Test
    fun navigateBackToExpensesFragment_backNavigation_toExpensesFragment() {
        onView(withId(R.id.expenses_list))
            .perform(
                actionOnItemAtPosition<ExpensesAdapter.ExpensesHolder>(
                    lastItemInTest,
                    click()
                )
            )

        onView(withId(R.id.editTextMerchantTitle)).check(matches(withText(expenseInTest.expenseVenueTitle)))
        pressBack()
        onView(withId(R.id.expenses_list)).check(matches(isDisplayed()))
    }

    @Test
    fun crossClickedNavigation_backNavigation_toExpensesFragment() {

        onView(withId(R.id.expenses_list))
            .perform(
                actionOnItemAtPosition<ExpensesAdapter.ExpensesHolder>(
                    lastItemInTest,
                    click()
                )
            )

        onView(withId(R.id.editTextMerchantTitle)).check(matches(withText(expenseInTest.expenseVenueTitle)))

        onView(withId(R.id.close)).perform(click())
        onView(withId(R.id.expenses_list)).check(matches(isDisplayed()))
    }


}
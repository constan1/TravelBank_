package com.exercise.travelbank_.ui.fragments

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.exercise.travelbank_.R
import com.exercise.travelbank_.bindingadapters.adapters.ExpensesAdapter
import com.exercise.travelbank_.models.ExpensesDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

class ExpenseDetailsFragmentTest {

    @ExperimentalCoroutinesApi
    @get: Rule
    val activityScenerio = ActivityScenarioRule(MainActivity::class.java)


    val lastItemInTest = 3
    val expenseInTest = ExpensesDTO(
        1.82, null, "2021-07-09T00:00:00.000Z",
        "Morning coffee", "Coffee", "perDiem", "EUR"
    )


    @Test
    fun expenseFragmentLaunch_expensesFragment_isVisibleOnLaunch() {
        Espresso.onView(ViewMatchers.withId(R.id.expenses_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun expenseDetailFragmentDataPassed_expenseDetailsFragment_allDataToDetailsPassedSuccessfully() {

        Espresso.onView(ViewMatchers.withId(R.id.expenses_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ExpensesAdapter.ExpensesHolder>(
                    lastItemInTest,
                    ViewActions.click()
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.editTextMerchantTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(expenseInTest.expenseVenueTitle)))

        Espresso.onView(ViewMatchers.withId(R.id.editTextAmount))
            .check(ViewAssertions.matches(ViewMatchers.withText(expenseInTest.amount.toString())))

        Espresso.onView(ViewMatchers.withId(R.id.editTextdate))
            .check(ViewAssertions.matches(ViewMatchers.withText("Jul 09, 2021")))

        Espresso.onView(ViewMatchers.withId(R.id.currencyChip))
            .check(ViewAssertions.matches(ViewMatchers.withText(expenseInTest.currencyCode)))

        Espresso.onView(ViewMatchers.withId(R.id.ccategory_value))
            .check(ViewAssertions.matches(ViewMatchers.withText(expenseInTest.tripBudgetCategory)))

        Espresso.onView(ViewMatchers.withId(R.id.editText_details_description))
            .check(ViewAssertions.matches(ViewMatchers.withText(expenseInTest.description)))
    }
}
package com.nauval.aplikasigithubuser

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.nauval.aplikasigithubuser.helper.EspressoIdleRes
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainLandingPageActivityUITest: TestCase() {

    private val dummyUsername = "NauvalNC"
    private val dummyMinimalUserFound = 1

    @Before
    public override fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdleRes.idleRes)
        ActivityScenario.launch(MainLandingPageActivity::class.java)
        super.setUp()
    }

    @After
    public override fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdleRes.idleRes)
        super.tearDown()
    }

    @Test
    fun searchTotalUserAtLeast() {
        onView(withId(R.id.search_menu)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.search)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(isAssignableFrom(SearchView::class.java)).perform(setQueryAnSubmitOnSearchView(dummyUsername))
        onView(withId(R.id.found_info)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).apply {
            check(matches(isDisplayed()))
            check(matches(foundUserAtLeast(dummyMinimalUserFound)))
        }
    }

    companion object {
        private fun setQueryAnSubmitOnSearchView(query: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> =
                    allOf(isDisplayed(), hasFocus(), isAssignableFrom(SearchView::class.java))

                override fun getDescription(): String = "Set query for search view"

                override fun perform(uiController: UiController, view: View) {
                    (view as SearchView).setQuery(query, true)
                }
            }
        }

        private fun foundUserAtLeast(numOfItem: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("Total users found is sufficient: $numOfItem")
                }

                override fun matchesSafely(item: RecyclerView?): Boolean =
                    item?.adapter?.itemCount!! >= numOfItem
            }
        }
    }
}
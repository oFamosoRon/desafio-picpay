package com.picpay.desafio.android.ui.users_list

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.picpay.desafio.android.R
import com.picpay.desafio.android.di.mockedViewModelUiState
import com.picpay.desafio.android.domain.model.User
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest


class UsersFragmentTest : KoinTest {

    private lateinit var scenario: FragmentScenario<UsersFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun titleIsDisplayed() {
        onView(withText(R.string.title)).check(matches(isDisplayed()))
    }

    @Test
    fun showsLoaderIndicatorBeforeLoadingData() {

        //when viewModel state is loading
        mockedViewModelUiState.value = UiState.Loading
        //the progress bar is shown on the screen
        onView(withId(R.id.user_list_progress_bar)).check(matches(isDisplayed()))
        //And the list of contacts is not
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))

        //when the viewModel state changes to success
        mockedViewModelUiState.value = UiState.Success(listOf(user))

        //the progress bar is hidden
        onView(withId(R.id.user_list_progress_bar)).check(matches(not(isDisplayed())))
        //And the list of contacts is displayed
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun displaysAllItemsInList() {
        //Given: viewModel state is success with valid data
        val users = listOf(user, user, user, user, user)
        mockedViewModelUiState.value = UiState.Success(users)

        onView(withId(R.id.recyclerView))
            //Then: recycler view has all the data from the state
            .check(matches(hasChildCount(users.size)))
    }

    @Test
    fun hidesUiComponentsInErrorState() {
        //Given: viewModel state is error
        mockedViewModelUiState.value = UiState.Error("no data")

        //Then: progress bar is not shown
        onView(withId(R.id.user_list_progress_bar)).check(matches(not(isDisplayed())))
        //And: list is not shown
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun itemsWithNullFieldsShowDefaultText() {
        //Given: viewModel state is success data that contains null fields
        val users = listOf(
            userWithNullFields,
            userWithNullFields,
            userWithNullFields,
        )
        mockedViewModelUiState.value = UiState.Success(users)

        for (position in users.indices) {
            onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))

            // Then: Assert that the null fields display default message
            onView(withId(R.id.recyclerView))
                //Name
                .check(
                    matches(
                        atPosition(
                            position = position,
                            viewId = R.id.name,
                            matcher = withText(R.string.name_not_provided)
                        )
                    )
                )
                //Username
                .check(
                    matches(
                        atPosition(
                            position = position,
                            viewId = R.id.username,
                            matcher = withText(R.string.username_not_provided)
                        )
                    )
                )
        }
    }

    private fun atPosition(position: Int, viewId: Int, matcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val viewHolder = item?.findViewHolderForAdapterPosition(position)
                val targetView =
                    viewHolder?.itemView?.findViewById<View>(viewId) // Replace with your actual TextView ID
                return matcher.matches(targetView)
            }

            override fun describeTo(description: Description?) {
                description?.appendText("has item at position $position")
            }
        }
    }

}

private val user = User(
    id = 1,
    name = "Carli Carroll",
    img = "https://randomuser.me/api/portraits/men/2.jpg",
    username = "carli.carroll"
)

private val userWithNullFields = User(
    id = 1,
    name = null,
    img = null,
    username = null
)
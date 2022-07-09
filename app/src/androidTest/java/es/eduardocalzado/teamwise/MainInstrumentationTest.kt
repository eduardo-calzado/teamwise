package es.eduardocalzado.teamwise

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
  import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import es.eduardocalzado.teamwise.R.id.teamsRecycler
import es.eduardocalzado.teamwise.ui.MainHostActivity
import org.junit.Rule
import org.junit.Test

class MainInstrumentationTest {

    @get:Rule
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule
    val activityRule = ActivityScenarioRule(MainHostActivity::class.java)

    @Test
    // here, the `test_name` fun name is not correct. It's not possible to use it.
    fun click_a_team_and_navigate_to_detail() {
        onView(withId(teamsRecycler))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3,
                    click()
            ))
        onView(withId(R.id.team_detail_info))
            .check(matches(withText("Founded in 1898")))
    }
}
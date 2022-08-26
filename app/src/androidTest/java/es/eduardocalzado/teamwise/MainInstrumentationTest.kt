package es.eduardocalzado.teamwise

import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.adevinta.android.barista.assertion.BaristaAssertions.assertAny
import com.adevinta.android.barista.assertion.BaristaImageViewAssertions.assertHasAnyDrawable
import com.adevinta.android.barista.assertion.BaristaImageViewAssertions.assertHasDrawable
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertCustomAssertionAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDrawableDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickBack
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.adevinta.android.barista.interaction.BaristaScrollInteractions.safelyScrollTo
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.eduardocalzado.teamwise.data.server.OkHttp3IdlingResource
import es.eduardocalzado.teamwise.ui.MainHostActivity
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class MainInstrumentationTest {

    @get:Rule
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule
    val activityRule = ActivityScenarioRule(MainHostActivity::class.java)

    @Before
    fun setUp() {
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }
        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    // important: disable animations on developer options
    // here, the `test_name` fun name is not correct. It's not possible to use it.
    // **** using Barista as better addon of Espresso library.
    fun happy_path() {
        // teams -> click on a team
        clickListItem(R.id.teams_recycler, 3);
        // team details -> "Wolves": The team depends on the list loaded by the filter / geolocation
        assertDisplayed("Wolves");
        // team details -> Wait to load team data.
        sleep(1, TimeUnit.SECONDS);
        // team details -> Swipe down until see the button
        safelyScrollTo(R.id.team_detail_players_button)
        // team details -> Click on players
        clickOn(R.id.team_detail_players_button)
        // players -> Wait to load players data.
        sleep(1, TimeUnit.SECONDS);
        // players -> click on a player
        clickListItem(R.id.players_recycler, 0);
        // player details -> "Nélson Semedo" is the first player
        assertDisplayed("Nélson");
        // back
        clickBack();
        clickBack();
    }

    @Test
    fun mark_a_team_as_favorite() {
        // teams -> click on a team
        clickListItem(R.id.teams_recycler, 3);
        // team details -> "Wolves": The team depends on the list loaded by the filter / geolocation
        assertDisplayed("Wolves");
        // team details -> Mark it as favorite
        clickOn(R.id.team_detail_favorite)
        // goBack
        clickBack()
        // teams -> check if it is favorite in the list
        assertDisplayedAtPosition(R.id.teams_recycler, 0, R.id.team_name, "Wolves");
    }

    @Test
    fun mark_a_team_as_unfavorite() {
        // teams -> click on a team
        clickListItem(R.id.teams_recycler, 0);
        // team details -> "Wolves": The team depends on the list loaded by the filter / geolocation
        assertDisplayed("Wolves");
        // team details -> Mark it as favorite
        clickOn(R.id.team_detail_favorite)
        // goBack
        clickBack()
        // teams -> check if it is favorite in the list
        assertDisplayedAtPosition(R.id.teams_recycler, 3, R.id.team_name, "Wolves");
    }
}
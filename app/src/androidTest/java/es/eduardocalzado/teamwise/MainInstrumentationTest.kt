package es.eduardocalzado.teamwise

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaAutoCompleteTextViewInteractions.writeToAutoComplete
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickBack
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.adevinta.android.barista.interaction.BaristaMenuClickInteractions.clickMenu
import com.adevinta.android.barista.interaction.BaristaScrollInteractions.safelyScrollTo
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import es.eduardocalzado.teamwise.data.server.OkHttp3IdlingResource
import es.eduardocalzado.teamwise.ui.MainHostActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import java.util.concurrent.TimeUnit

/**
 * IMPORTANT: It is required to start testing with default teams: England - Premier League - 2021
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    /**
     * important: disable animations on developer options
     * here, the `test_name` fun name is not correct. It's not possible to use it.
     * **** using Barista as better addon of Espresso library.
     */
    @Test
    fun action_init() {
        // teams -> action: show filters
        clickMenu(R.id.show_filters);
        // teams -> filters shown
        sleep(1, TimeUnit.SECONDS)
        assertDisplayed(R.id.teams_filter_layout);
        // teams -> pop up countries
        clickOn(R.id.teams_til_country)
        // teams -> click on England
        onView(withText("England"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click());
        // teams -> click on submit
        clickOn(R.id.teams_filter_submit_button)
        // teams -> check finish state
        sleep(1, TimeUnit.SECONDS)
    }

    @Test
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

    @Test
    fun show_filters_and_submit_a_request() {
        // teams -> check initial state
        assertDisplayedAtPosition(R.id.teams_recycler, 0, R.id.team_name, "Manchester United");
        // teams -> filters hidden
        assertNotDisplayed(R.id.teams_filter_layout);
        // teams -> action: show filters
        clickMenu(R.id.show_filters);
        // teams -> filters shown
        sleep(1, TimeUnit.SECONDS)
        assertDisplayed(R.id.teams_filter_layout);
        // teams -> pop up countries
        clickOn(R.id.teams_til_country)
        // teams -> click on Spain
        onView(withText("Spain"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click());
        // teams -> click on submit
        clickOn(R.id.teams_filter_submit_button)
        // teams -> check finish state
        sleep(1, TimeUnit.SECONDS)
        assertDisplayedAtPosition(R.id.teams_recycler, 0, R.id.team_name, "Barcelona");
    }

    @Test
    fun use_search_bar() {
        // teams -> click on the search bar
        clickMenu(R.id.main_search_team);
        // teams -> write "B"
        writeToAutoComplete(R.id.main_search_team, "B");
        // teams -> the list count should be reduced to 3
        sleep(1, TimeUnit.SECONDS)
        assertListItemCount(R.id.teams_recycler, 3)
    }
}
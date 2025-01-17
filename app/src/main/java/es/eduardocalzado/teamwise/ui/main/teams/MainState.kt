package es.eduardocalzado.teamwise.ui.main.teams

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.di.LeagueId
import es.eduardocalzado.teamwise.di.SeasonId
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.prefs
import es.eduardocalzado.teamwise.ui.common.PermissionRequester
import es.eduardocalzado.teamwise.ui.common.toggleVisibility
import es.eduardocalzado.teamwise.ui.main.teams.MainState.MainFilters.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(context, scope, locationPermissionRequester, navController)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val locationPermissionRequester: PermissionRequester,
    private val navController: NavController,
) {

    fun onTeamClicked(teamId: Int, leagueId: Int, seasonId: Int) {
        val navAction = MainFragmentDirections.actionMainToDetail(teamId, leagueId, seasonId)
        navController.navigate(navAction)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }

    fun sortTeams(teams: List<Team>): List<Team> {
        return teams.sortedByDescending { it.favorite }
    }

    enum class MainFilters { Country, League, Season }
    fun loadData(filter: MainFilters, country: String? = null): ArrayAdapter<String> {
        return when (filter) {
            Country -> {
                val countries = context.resources.getStringArray(R.array.countries)
                ArrayAdapter(context, R.layout.dropdown_item, countries)
            }
            League -> {
                val leagues = when(country) {
                    "Spain" -> context.resources.getStringArray(R.array.spanish_leagues)
                    "England" -> context.resources.getStringArray(R.array.england_leagues)
                    "Italy" -> context.resources.getStringArray(R.array.italy_leagues)
                    else -> emptyArray()
                }
                ArrayAdapter(context, R.layout.dropdown_item, leagues)
            }
            Season -> {
                val seasons = context.resources.getStringArray(R.array.seasons)
                ArrayAdapter(context, R.layout.dropdown_item, seasons)
            }
        }
    }

    fun toggleVisibility(binding: FragmentMainBinding) {
        binding.teamsFilterLayout.toggleVisibility()
        binding.teamsHeaderTitle.toggleVisibility()
    }
}
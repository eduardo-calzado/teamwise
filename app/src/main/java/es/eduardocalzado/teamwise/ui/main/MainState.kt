package es.eduardocalzado.teamwise.ui.main

import android.Manifest
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.ui.common.PermissionRequester
import es.eduardocalzado.teamwise.ui.common.toggleVisibility
import es.eduardocalzado.teamwise.ui.main.MainState.MainFilters.*
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

    fun onTeamClicked(teamId: Int) {
        val navAction = MainFragmentDirections.actionMainToDetail(teamId)
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
    fun initFilter(filter: MainFilters, country: String? = null): ArrayAdapter<String> {
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
                    else -> context.resources.getStringArray(R.array.england_leagues)
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
        binding.filterView.toggleVisibility()
        binding.headerTitle.toggleVisibility()
    }
}
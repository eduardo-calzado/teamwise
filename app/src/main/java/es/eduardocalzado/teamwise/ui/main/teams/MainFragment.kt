package es.eduardocalzado.teamwise.ui.main.teams

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SearchView.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.domain.TeamLeague
import es.eduardocalzado.teamwise.domain.getTeamLeagueIdByName
import es.eduardocalzado.teamwise.domain.getTeamLeagueNameById
import es.eduardocalzado.teamwise.prefs
import es.eduardocalzado.teamwise.ui.common.toggleVisibility
import es.eduardocalzado.teamwise.ui.main.teams.MainState.MainFilters.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), OnQueryTextListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainState: MainState
    private lateinit var binding: FragmentMainBinding
    private val adapter = TeamAdapter {
        mainState.onTeamClicked(
            teamId = it.id,
            leagueId = getFiltersData().second,
            seasonId = getFiltersData().third)
        clearSearch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mainState = buildMainState()
        // --
        binding = FragmentMainBinding.bind(view).apply {
            teamsRecycler.adapter = adapter
            teamsFilterSubmitButton.setOnClickListener {
                val (country, league, season) = getFiltersData()
                viewModel.onSubmitClicked(country, league, season)
                mainState.toggleVisibility(binding)
            }
            teamsFilterClearButton.setOnClickListener {
                viewModel.onDeleteClicked()
            }
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                 viewModel.state.collect {
                     binding.loading = it.loading
                     binding.teams = it.teams?.let(mainState::sortTeams)
                     binding.error = it.error?.let(mainState::errorToString)
                 }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadFilters()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu.findItem(R.id.main_search_team)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.show_filters -> { mainState.toggleVisibility(binding); true }
            R.id.main_location -> {
                mainState.requestLocationPermission {
                    viewLifecycleOwner.lifecycleScope.launch {
                        with(binding) {
                            loadFilters(getLocation())
                            mainState.toggleVisibility(binding)
                            teamsFilterSubmitButton.run { callOnClick() }
                        }
                    }
                }
                true
            }
            else -> false
        }
    }

    /**
     * loadFilters. Load data of the filters. BE CAUTION, they loose the context when you go back,
     * and that's the reason to be called in onResume.
     */
    private fun loadFilters(region: String? = null) {
        with(binding) {
            val countryAdapter = mainState.loadData(Country)
            teamsTilTvCountry.setAdapter(countryAdapter)
            val country = when (region.isNullOrEmpty()) {
                true -> when (prefs.countryId.isNullOrEmpty()) {
                    true -> countryAdapter.getItem(0).toString()
                    false -> prefs.countryId
                }
                false -> region
            }
            teamsTilTvCountry.setText(country, false)

            val leaguesAdapter = mainState.loadData(League, country)
            teamsTilTvLeague.setAdapter(leaguesAdapter)
            when (prefs.leagueId == -1) {
                true -> teamsTilTvLeague.setText(leaguesAdapter.getItem(0).toString())
                false -> teamsTilTvLeague.setText(getTeamLeagueNameById(prefs.leagueId), false)
            }

            val seasonsAdapter = mainState.loadData(Season)
            teamsTilTvSeason.setAdapter(seasonsAdapter)
            when (prefs.seasonId == -1) {
                true -> teamsTilTvSeason.setText(seasonsAdapter.getItem(0).toString(), false)
                false -> teamsTilTvSeason.setText(prefs.seasonId.toString(), false)
            }

            // -- onItemClickListener
            teamsTilTvCountry.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                val leaguesAdapter2 = mainState.loadData(League, teamsTilTvCountry.text.toString())
                teamsTilTvLeague.setText(leaguesAdapter2.getItem(0).toString())
                teamsTilTvLeague.setAdapter(leaguesAdapter2)
            }

            if (prefs.firstInstall) {
                teamsFilterSubmitButton.callOnClick()
                mainState.toggleVisibility(binding)
                prefs.firstInstall = false
            }
        }
    }


    /**
     * getLocation. returns the country where the user is located
     */
    private suspend fun getLocation() = viewModel.onRequestLastRegion()

    /**
     * getFiltersData. Return the triple value of the filters:
     * @return country@string, league@int, season@int
     */
    private fun getFiltersData(): Triple<String, Int, Int> {
        val country = binding.teamsTilTvCountry.text.toString()
        val league = getTeamLeagueIdByName(binding.teamsTilTvLeague.text.toString())
        val season = binding.teamsTilTvSeason.text.toString().toInt()

        return Triple(country, league, season)
    }

    /**
     * onQuery*. Methods used by the search view
     * @return true
     */
    override fun onQueryTextSubmit(p0: String?): Boolean {
        searchDatabase(p0 ?: "")
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        searchDatabase(p0 ?: "")
        return true
    }

    private fun searchDatabase(query: String) {
        viewModel.searchTeams("%$query%")
    }

    private fun clearSearch() {
        searchDatabase("%%")
    }
}

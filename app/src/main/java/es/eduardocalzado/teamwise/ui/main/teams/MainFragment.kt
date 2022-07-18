package es.eduardocalzado.teamwise.ui.main.teams

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SearchView.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.domain.getTeamLeagueIdByName
import es.eduardocalzado.teamwise.ui.main.teams.MainState.MainFilters.*
import kotlinx.coroutines.flow.collect
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
            }
            teamsFilterClearButton.setOnClickListener{
                viewModel.onDeleteClicked()
            }
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                 viewModel.state.collect { it ->
                     binding.loading = it.loading
                     binding.teams = it.teams?.let(mainState::sortTeams)
                     binding.error = it.error?.let(mainState::errorToString)
                 }
            }
        }
        // --
        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }

    override fun onResume() {
        super.onResume()
        // -- fill the filter's adapters
        with(binding) {
            // -- init filters
            teamsTilTvCountry.setAdapter(mainState.initFilter(Country))
            val country = teamsTilTvCountry.text.toString()
            teamsTilTvLeague.setAdapter(mainState.initFilter(League, country))
            teamsTilTvSeason.setAdapter(mainState.initFilter(Season))
            // -- handle nested filters
            teamsTilTvCountry.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                val leaguesAdapter = mainState.initFilter(League, teamsTilTvCountry.text.toString())
                teamsTilTvLeague.setText(leaguesAdapter.getItem(0).toString())
                teamsTilTvLeague.setAdapter(leaguesAdapter)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.main_search_team)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.show_filters -> { mainState.toggleVisibility(binding); true }
            else -> false
        }
    }

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
}

package es.eduardocalzado.teamwise.ui.main.teams

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.getTeamLeagueIdByName
import es.eduardocalzado.teamwise.ui.main.teams.MainState.MainFilters.*
import es.eduardocalzado.teamwise.usecases.sampleTeam
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

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
            /*
            // Header issues
            val gridLayoutManager = GridLayoutManager(context, 3)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) 3 else 1

                }
            }
            teamsRecycler.layoutManager = gridLayoutManager*/
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
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.showFilters -> { mainState.toggleVisibility(binding); true }
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
}

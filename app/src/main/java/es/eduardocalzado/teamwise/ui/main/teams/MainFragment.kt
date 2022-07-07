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
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.domain.getTeamLeagueIdByName
import es.eduardocalzado.teamwise.ui.main.teams.MainState.MainFilters.*
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
            recycler.adapter = adapter
            submitFilterButton.setOnClickListener {
                val (country, league, season) = getFiltersData()
                viewModel.onSubmitClicked(country, league, season)
            }
            clearFilterButton.setOnClickListener{
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
            autoCompleteTextViewCountry.setAdapter(mainState.initFilter(Country))
            val country = autoCompleteTextViewCountry.text.toString()
            autoCompleteTextViewLeague.setAdapter(mainState.initFilter(League, country))
            autoCompleteTextViewSeason.setAdapter(mainState.initFilter(Season))
            // -- handle nested filters
            autoCompleteTextViewCountry.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                val country = autoCompleteTextViewCountry.text.toString()
                val leaguesAdapter = mainState.initFilter(League, country)
                autoCompleteTextViewLeague.setText(leaguesAdapter.getItem(0).toString())
                autoCompleteTextViewLeague.setAdapter(leaguesAdapter)
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
        val country = binding.autoCompleteTextViewCountry.text.toString()
        val league = getTeamLeagueIdByName(binding.autoCompleteTextViewLeague.text.toString())
        val season = binding.autoCompleteTextViewSeason.text.toString().toInt()
        return Triple(country, league, season)
    }
}

package es.eduardocalzado.teamwise.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.ui.main.MainState.MainFilters.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainState: MainState
    private lateinit var binding: FragmentMainBinding
    private val adapter = TeamAdapter { mainState.onTeamClicked(teamId = it.id)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mainState = buildMainState()
        // --
        binding = FragmentMainBinding.bind(view).apply {
            // -- fill the team list adapter
            recycler.adapter = adapter
            // -- listener of submit button
            submitFilterButton.setOnClickListener {
                val country = "England"
                val league = 39
                val season = 2021
                viewModel.onSubmitClicked(country, league, season)
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
}

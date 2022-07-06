package es.eduardocalzado.teamwise.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.databinding.FragmentPlayersBinding
import es.eduardocalzado.teamwise.ui.common.setVisible
import es.eduardocalzado.teamwise.ui.common.toggleVisibility
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
            // -- fixing issues with action bat
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            // -- fill the filter's adapters
            autoCompleteTextViewCountry.setAdapter(mainState.initFilter(Country))
            autoCompleteTextViewSeason.setAdapter(mainState.initFilter(Season))
            autoCompleteTextViewCountry.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                val country = autoCompleteTextViewCountry.text.toString()
                val leaguesAdapter = mainState.initFilter(League, country)
                autoCompleteTextViewLeague.setText(leaguesAdapter.getItem(0).toString())
                autoCompleteTextViewLeague.setAdapter(leaguesAdapter)
            }
            // -- listener of submit button
            submitFilterButton.setOnClickListener(viewModel::onSubmitClicked)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.showFilters -> { mainState.toggleVisibility(binding)
                true
            }
            else -> false
        }
    }
}

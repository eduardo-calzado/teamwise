package es.eduardocalzado.teamwise.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainState: MainState

    private val adapter = TeamAdapter { mainState.onTeamClicked(teamId = it.id)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        // --
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                 viewModel.state.collect { it ->
                     binding.loading = it.loading
                     binding.teams = it.teams?.let { teams ->
                         teams.sortedByDescending { it.favorite }
                     }
                     binding.error = it.error?.let(mainState::errorToString)
                 }
            }
        }
        // --
        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }
}
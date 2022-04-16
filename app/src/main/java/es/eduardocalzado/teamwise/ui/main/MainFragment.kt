package es.eduardocalzado.teamwise.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import es.eduardocalzado.teamwise.App
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.data.network.TeamRepository
import es.eduardocalzado.teamwise.usecases.GetTeamsUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        val repository = TeamRepository(requireActivity().application as App)
        MainViewModelFactory(
            GetTeamsUseCase(repository),
            RequestTeamsUseCase(repository)
        )
    }

    private val adapter = TeamAdapter { mainState.onTeamClicked(teamId = it.id)}

    private lateinit var mainState: MainState

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
                 viewModel.state.collect {
                     binding.loading = it.loading
                     binding.teams = it.teams
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
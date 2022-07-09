package es.eduardocalzado.teamwise.ui.detail.players

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentPlayersBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamPlayersFragment : Fragment(R.layout.fragment_players) {

    private val viewModel: TeamPlayersViewModel by viewModels()

    private lateinit var teamPlayersState: TeamPlayersState
    private lateinit var binding: FragmentPlayersBinding
    private val adapter = TeamPlayersAdapter {
        teamPlayersState.onPlayerClicked(
            playerId = it.id
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamPlayersState = buildTeamPlayerState()
        // --
        binding = FragmentPlayersBinding.bind(view).apply {
            teamPlayersRecycler.adapter = adapter
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { it ->
                    binding.loading = it.loading
                    binding.players = it.players
                    binding.error = it.error?.let(teamPlayersState::errorToString)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onUiReady()
    }
}
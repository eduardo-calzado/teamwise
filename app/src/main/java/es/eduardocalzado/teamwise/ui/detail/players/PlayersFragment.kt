package es.eduardocalzado.teamwise.ui.detail.players

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentPlayersBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersFragment : Fragment(R.layout.fragment_players), SearchView.OnQueryTextListener {

    private val viewModel: PlayersViewModel by viewModels()

    private lateinit var playersState: PlayersState
    private lateinit var binding: FragmentPlayersBinding
    private val adapter = PlayersAdapter {
        playersState.onPlayerClicked(
            playerId = it.id
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        playersState = buildPlayersState()
        // --
        binding = FragmentPlayersBinding.bind(view).apply {
            playersRecycler.adapter = adapter
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { it ->
                    binding.loading = it.loading
                    binding.players = it.players
                    binding.error = it.error?.let(playersState::errorToString)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onUiReady()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_item_search)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
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
        viewModel.searchPlayers("%$query%")
    }
}
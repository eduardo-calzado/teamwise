package es.eduardocalzado.teamwise.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.constants.Constants.Companion.TEAM
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(TeamRepository(requireActivity() as AppCompatActivity))
    }

    private val adapter = TeamAdapter { viewModel.onTeamClicked(it)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.updateUi(it)
                }
            }
        }
    }

    private fun FragmentMainBinding.updateUi(state: MainViewModel.UiState) {
        with(this) {
            progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
            if (state.teams.isNullOrEmpty()) {
                if (progressBar.visibility == View.GONE) noresults.visibility = View.VISIBLE
                else noresults.visibility = View.GONE
            }
        }
        state.teams?.let {
            adapter.submitList(it)
        }
        state.navigateTo?.let {
            navigateTo(it)
        }
    }

    private fun navigateTo(team: Team) {
        val navAction = MainFragmentDirections.actionMainToDetail(team)
        findNavController().navigate(navAction)
    }
}
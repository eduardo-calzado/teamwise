package es.eduardocalzado.teamwise.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.App
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.model.network.TeamRepository
import es.eduardocalzado.teamwise.model.utils.PermissionRequester
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(TeamRepository(requireActivity().application as App))
    }

    private val adapter = TeamAdapter { mainState.onTeamClicked(team = it)}

    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        // --
        mainState = MainState(
            viewLifecycleOwner.lifecycleScope,
            PermissionRequester (
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            findNavController()
        )
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                 viewModel.state.collect {
                    binding.updateUi(it)
                }
            }
        }
        // --
        mainState.requestLocationPermission { viewModel.onUiReady() }
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
    }

}
package es.eduardocalzado.teamwise.ui.detail.stats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var detailState: DetailState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailState = buildDetailState()
        // --
        val binding = FragmentDetailBinding.bind(view).apply {
            teamDetailToolbar.setNavigationOnClickListener{findNavController().popBackStack()}
            teamDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
            teamDetailPlayersButton.setOnClickListener { detailState.onPlayersButtonClicked(viewModel.teamId, viewModel.leagueId, viewModel.seasonId) }
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.team = it.teamData
                    binding.teamStats = it.teamStats
                    binding.error = it.error?.let(detailState::errorToString)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onUiReady()
    }
}
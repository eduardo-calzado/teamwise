package es.eduardocalzado.teamwise.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentDetailBinding
import es.eduardocalzado.teamwise.ui.main.MainState
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
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.team = it.teamData
                    binding.updateUi(it)
                    binding.error = it.error?.let(detailState::errorToString)
                }
            }
        }
        viewModel.onUiReady()
    }

    private fun FragmentDetailBinding.updateUi(state: DetailViewModel.UiState) {
        val stats = state.teamStats
        with(this) {
            stats?.let {
                teamStatsProgressbar.visibility = if(state.loading) View.VISIBLE else View.GONE
                teamStatsInfo.text = buildSpannedString {
                    bold { appendLine("League") }
                    bold { append("\t- Name: ") }
                    appendLine(it.league_name)
                    bold { append("\t- Country: ") }
                    appendLine(it.league_country)
                    bold { appendLine("\nFixtures") }
                    bold { append("\t- Draws: ") }
                    appendLine(it.fixture_draws_total.toString())
                    bold { append("\t- Loses: ") }
                    appendLine(it.fixture_loses_total.toString())
                    bold { append("\t- Wins: ") }
                    append(it.fixture_wins_total.toString())
                }
            }
        }
    }
}
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                }
            }
        }
    }

    private fun FragmentDetailBinding.updateUi(state: DetailViewModel.UiState) {
        // val team = state.teamData
        val stats = state.teamStats?.stats

        with(this) {
            stats?.let {
                teamStatsProgressbar.visibility = if(state.loading) View.VISIBLE else View.GONE
                teamStatsInfo.text = buildSpannedString {
                    bold { appendLine("League") }
                    bold { append("\t- Name: ") }
                    appendLine(stats.league.name)
                    bold { append("\t- Country: ") }
                    appendLine(stats.league.country)
                    bold { appendLine("\nFixtures") }
                    bold { append("\t- Draws: ") }
                    appendLine(stats.fixtures.draws.total.toString())
                    bold { append("\t- Loses: ") }
                    appendLine(stats.fixtures.loses.total.toString())
                    bold { append("\t- Wins: ") }
                    append(stats.fixtures.wins.total.toString())
                }
            }
        }
    }
}
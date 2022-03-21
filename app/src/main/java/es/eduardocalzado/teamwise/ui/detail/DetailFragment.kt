package es.eduardocalzado.teamwise.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.eduardocalzado.teamwise.App
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentDetailBinding
import es.eduardocalzado.teamwise.model.extensions.loadUrl
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            TeamRepository(requireActivity().application as App),
            requireNotNull(safeArgs.team)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        // --
        binding.teamDetailToolbar.setNavigationOnClickListener{findNavController().popBackStack()}
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.updateUi(it)
                }
            }
        }
    }

    private fun FragmentDetailBinding.updateUi(state: DetailViewModel.UiState) {
        val team = state.teamData
        val stats = state.teamStats?.stats

        with(this) {
            team?.let {
                if (it.code.isNullOrEmpty()) teamDetailToolbar.title = it.name
                else teamDetailToolbar.title = "["+it.code+"] "+it.name
                teamDetailImage.loadUrl(it.logo)
                teamDetailInfo.text = buildSpannedString {
                    bold { append("Founded in ") }
                    append(it.founded.toString())
                }
                teamDetailBackground.loadUrl(it.stadiumImage)
                teamVenueInfo.text = buildSpannedString {
                    bold { appendLine("Stadium") }
                    italic { appendLine("\t"+it.name) }
                    appendLine("\tAt "+it.address+" in "+it.city)
                    append("\tCapacity for "+it.capacity.toString()+" and "+it.surface+" surface.")
                }
            }
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
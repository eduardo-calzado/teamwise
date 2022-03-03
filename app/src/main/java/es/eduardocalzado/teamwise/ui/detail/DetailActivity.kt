package es.eduardocalzado.teamwise.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.lifecycle.lifecycleScope
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.model.constants.Constants.Companion.TEAM
import es.eduardocalzado.teamwise.databinding.ActivityDetailBinding
import es.eduardocalzado.teamwise.databinding.ActivityMainBinding
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.extensions.loadUrl
import es.eduardocalzado.teamwise.model.network.TeamRepository
import es.eduardocalzado.teamwise.ui.main.MainViewModel
import es.eduardocalzado.teamwise.ui.main.MainViewModelFactory
import es.eduardocalzado.teamwise.ui.main.TeamAdapter
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            TeamRepository(this),
            requireNotNull(intent.getParcelableExtra(TEAM))
        )
    }
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // --
        viewModel.state.observe(this) {
            updateUi(it)
        }
    }

    private fun updateUi(state: DetailViewModel.UiState) {
        val details = state.teamData?.details
        val venue = state.teamData?.venue
        val stats = state.teamStats?.stats

        with(binding) {
            details?.let {
                if (it.code?.isNullOrEmpty() == true) teamDetailToolbar.title = it.name
                else teamDetailToolbar.title = "["+it.code+"] "+it.name
                teamDetailImage.loadUrl(it.logo)
                teamDetailInfo.text = buildSpannedString {
                    bold { append("Founded in ") }
                    append(it.founded.toString())
                }
            }
            venue?.let {
                teamDetailBackground.loadUrl(it.image)
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
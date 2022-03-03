package es.eduardocalzado.teamwise.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.lifecycle.lifecycleScope
import es.eduardocalzado.teamwise.model.constants.Constants.Companion.TEAM
import es.eduardocalzado.teamwise.databinding.ActivityDetailBinding
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.extensions.loadUrl
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private val teamRepository by lazy { TeamRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Team>(TEAM)?.run {
            binding.teamDetailBackground.loadUrl(venue.image)
            if (details.code?.isNullOrEmpty() == true) binding.teamDetailToolbar.title = details.name
            else binding.teamDetailToolbar.title = "["+details.code+"] "+details.name
            binding.teamDetailImage.loadUrl(details.logo)
            binding.teamDetailInfo.text = buildSpannedString {
                bold { append("Founded in ") }
                append(details.founded.toString())
            }

            binding.teamVenueInfo.text = buildSpannedString {
                bold { appendLine("Stadium") }
                italic { appendLine("\t"+venue.name) }
                appendLine("\tAt "+venue.address+" in "+venue.city)
                append("\tCapacity for "+venue.capacity.toString()+" and "+venue.surface+" surface.")
            }

            lifecycleScope.launch {
                binding.teamStatsProgressbar.visibility = View.VISIBLE
                val res =  teamRepository.getTeamStats(details.id)
                Thread.sleep(1000)
                if (res.errors.isEmpty()) {
                    Log.d(this.toString(), res.stats.toString())
                    binding.teamStatsInfo.text = buildSpannedString {
                        bold { appendLine("League") }
                        bold { append("\t- Name: ") }
                        appendLine(res.stats.league.name)
                        bold { append("\t- Country: ") }
                        appendLine(res.stats.league.country)
                        bold { appendLine("\nFixtures") }
                        bold { append("\t- Draws: ") }
                        appendLine(res.stats.fixtures.draws.total.toString())
                        bold { append("\t- Loses: ") }
                        appendLine(res.stats.fixtures.loses.total.toString())
                        bold { append("\t- Wins: ") }
                        append(res.stats.fixtures.wins.total.toString())
                    }
                }
                Log.d(this.toString(), "errors: "+res.errors.toString())
                binding.teamStatsProgressbar.visibility = View.GONE
            }
        }
    }
}
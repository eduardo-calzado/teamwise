package es.eduardocalzado.teamwise.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.lifecycleScope
import es.eduardocalzado.teamwise.constants.Constants.Companion.LEAGUE
import es.eduardocalzado.teamwise.constants.Constants.Companion.SEASON
import es.eduardocalzado.teamwise.constants.Constants.Companion.TEAM
import es.eduardocalzado.teamwise.databinding.ActivityDetailBinding
import es.eduardocalzado.teamwise.extensions.loadUrl
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.network.APIFootballConnection
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Team>(TEAM)?.run {
            binding.teamDetailToolbar.title = details.code
            binding.teamDetailImage.loadUrl(details.logo)
            binding.teamDetailSummary.text = details.name
            binding.teamDetailInfo.text = buildSpannedString {
                bold { append("Founded: ") }
                append(details.founded.toString())
            }

            binding.teamVenueInfo.text = buildSpannedString {
                bold { append("Venue name: ") }
                appendLine(venue.name)
                bold { append("Address: ") }
                appendLine(venue.address)
                bold { append("City: ") }
                appendLine(venue.city)
                bold { append("Capacity ") }
                appendLine(venue.capacity.toString())
                bold { append("Surface: ") }
                append(venue.surface)
            }

            lifecycleScope.launch {
                binding.teamStatsProgressbar.visibility = View.VISIBLE
                val res = APIFootballConnection.service.getTeamStats(LEAGUE, SEASON, details.id)
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
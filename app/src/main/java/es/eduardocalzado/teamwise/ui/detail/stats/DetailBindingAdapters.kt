package es.eduardocalzado.teamwise.ui.detail.stats

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.InfoItem
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.ui.common.CustomListAdapter

@BindingAdapter("team")
fun RecyclerView.setTeam(team: Team?) {
    if (team != null) {
        (adapter as? CustomListAdapter)?.submitList(createTeamAttributes(team))
    }
}

@BindingAdapter("stats")
fun RecyclerView.setTeamStats(stats: Team.Stats?) {
    if (stats != null) {
        (adapter as? CustomListAdapter)?.submitList(createTeamStatsAttributes(stats))
    }
}

/**
 * createTeamAttributes. From a team with some values, creates a list of items.
 */
fun createTeamAttributes(team: Team): List<InfoItem> {
    val items = mutableListOf<InfoItem>()
    items.add(InfoItem("Information", "", true))
    items.add(InfoItem("Name", team.name))
    items.add(InfoItem("Founded", team.founded.toString()))
    items.add(InfoItem("Code", team.code.toString()))
    items.add(InfoItem("Country", team.country))

    items.add(InfoItem("Stadium", "", true))
    items.add(InfoItem("Name", team.stadiumName))
    items.add(InfoItem("Address", team.address.toString()))
    items.add(InfoItem("Capacity", team.capacity.toString()))
    items.add(InfoItem("Surface", team.surface.toString()))

    return items
}

/**
 * createTeamAttributes. From a team with some values, creates a list of items.
 */
fun createTeamStatsAttributes(stats: Team.Stats): List<InfoItem> {
    val items = mutableListOf<InfoItem>()
    items.add(InfoItem("League", "", true))
    items.add(InfoItem("Name", stats.league.name.toString()))
    items.add(InfoItem("Country", stats.league.country.toString()))
    items.add(InfoItem("Season", stats.league.season.toString()))

    items.add(InfoItem("Form", "", true, isNested = true))
    items.add(InfoItem("(L: Lose, D: Draw, W: Win)", stats.form.toString(), isNested = true))

    items.add(InfoItem("Fixtures", "", true, isNested = true))
    items.add(InfoItem("Draws (Home, Away)", stats.fixtures.draws?.home.toString()+", "+stats.fixtures.draws?.away.toString(), isNested = true))
    items.add(InfoItem("Loses (Home, Away)", stats.fixtures.loses?.home.toString()+", "+stats.fixtures.loses?.away.toString(), isNested = true))
    items.add(InfoItem("Wins (Home, Away)", stats.fixtures.wins?.home.toString()+", "+stats.fixtures.wins?.away.toString(), isNested = true))

    items.add(InfoItem("Biggest", "", true, isNested = true))
    items.add(InfoItem("Streak (Wins, Draws, Loses)", stats.biggest.streak?.wins.toString()+", "+stats.biggest.streak?.draws.toString()+", "+stats.biggest.streak?.loses.toString(), isNested = true))
    items.add(InfoItem("Wins (Home, Away)", stats.biggest.wins?.home.toString()+", "+stats.biggest.wins?.away.toString(), isNested = true))
    items.add(InfoItem("Loses (Home, Away)", stats.biggest.loses?.home.toString()+", "+stats.biggest.loses?.away.toString(), isNested = true))

    items.add(InfoItem("Clean Sheet", "", true, isNested = true))
    items.add(InfoItem("Draws (Home, Away)", stats.fixtures.draws?.home.toString()+", "+stats.fixtures.draws?.away.toString(), isNested = true))
    items.add(InfoItem("Loses (Home, Away)", stats.fixtures.loses?.home.toString()+", "+stats.fixtures.loses?.away.toString(), isNested = true))
    items.add(InfoItem("Wins (Home, Away)", stats.fixtures.wins?.home.toString()+", "+stats.fixtures.wins?.away.toString(), isNested = true))

    return items
}
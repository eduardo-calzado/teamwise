package es.eduardocalzado.teamwise.ui.detail.stats

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.ListItem
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
fun createTeamAttributes(team: Team): List<ListItem> {
    val items = mutableListOf<ListItem>()
    items.add(ListItem("Information", "", true))
    items.add(ListItem("Name", team.name))
    items.add(ListItem("Founded", team.founded.toString()))
    items.add(ListItem("Code", team.code.toString()))
    items.add(ListItem("Country", team.country))

    items.add(ListItem("Stadium", "", true))
    items.add(ListItem("Name", team.stadiumName))
    items.add(ListItem("Address", team.address.toString()))
    items.add(ListItem("Capacity", team.capacity.toString()))
    items.add(ListItem("Surface", team.surface.toString()))

    return items
}

/**
 * createTeamAttributes. From a team with some values, creates a list of items.
 */
fun createTeamStatsAttributes(stats: Team.Stats): List<ListItem> {
    val items = mutableListOf<ListItem>()
    items.add(ListItem("League", "", true))
    items.add(ListItem("Name", stats.league.name.toString()))
    items.add(ListItem("Country", stats.league.country.toString()))
    items.add(ListItem("Season", stats.league.season.toString()))

    items.add(ListItem("Form", "", true, isNested = true))
    items.add(ListItem("(L: Lose, D: Draw, W: Win)", stats.form.toString(), isNested = true))

    items.add(ListItem("Fixtures", "", true, isNested = true))
    items.add(ListItem("Draws (Home, Away)", stats.fixtures.draws?.home.toString()+", "+stats.fixtures.draws?.away.toString(), isNested = true))
    items.add(ListItem("Loses (Home, Away)", stats.fixtures.loses?.home.toString()+", "+stats.fixtures.loses?.away.toString(), isNested = true))
    items.add(ListItem("Wins (Home, Away)", stats.fixtures.wins?.home.toString()+", "+stats.fixtures.wins?.away.toString(), isNested = true))

    items.add(ListItem("Biggest", "", true, isNested = true))
    items.add(ListItem("Streak (Wins, Draws, Loses)", stats.biggest.streak?.wins.toString()+", "+stats.biggest.streak?.draws.toString()+", "+stats.biggest.streak?.loses.toString(), isNested = true))
    items.add(ListItem("Wins (Home, Away)", stats.biggest.wins?.home.toString()+", "+stats.biggest.wins?.away.toString(), isNested = true))
    items.add(ListItem("Loses (Home, Away)", stats.biggest.loses?.home.toString()+", "+stats.biggest.loses?.away.toString(), isNested = true))

    items.add(ListItem("Clean Sheet", "", true, isNested = true))
    items.add(ListItem("Draws (Home, Away)", stats.fixtures.draws?.home.toString()+", "+stats.fixtures.draws?.away.toString(), isNested = true))
    items.add(ListItem("Loses (Home, Away)", stats.fixtures.loses?.home.toString()+", "+stats.fixtures.loses?.away.toString(), isNested = true))
    items.add(ListItem("Wins (Home, Away)", stats.fixtures.wins?.home.toString()+", "+stats.fixtures.wins?.away.toString(), isNested = true))

    return items
}
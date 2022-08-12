package es.eduardocalzado.teamwise.ui.detail.player

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.ListItem
import es.eduardocalzado.teamwise.framework.isNull
import es.eduardocalzado.teamwise.ui.common.CustomListAdapter

@BindingAdapter("info")
fun RecyclerView.setInfo(player: Player?) {
    if (player != null) {
        (adapter as? CustomListAdapter)?.submitList(createPlayerAttributes(player))
    }
}

/**
 * createPlayerAttributes. From a player with some values, creates a list of items.
 */
fun createPlayerAttributes(player: Player): List<ListItem> {
    val items = mutableListOf<ListItem>()
    items.add(ListItem("Information", "", true))
    items.add(ListItem("First Name", player.firstName.toString()))
    items.add(ListItem("Last Name", player.lastName.toString()))
    items.add(ListItem("Age", player.age.toString()))
    items.add(ListItem("Height", player.height.toString()))
    items.add(ListItem("Weight", player.weight.toString()))
    items.add(ListItem("Nationality", player.nationality.toString()))
    items.add(ListItem("Injured", player.injured.toString()))

    items.add(ListItem("Team", "", true))
    items.add(ListItem("Name", player.statistics.first().team.name.toString()))

    player.statistics.forEach {

        items.add(ListItem("League", "", true))
        items.add(ListItem("Name", it.league.name.toString()))

        items.add(ListItem("Games", "", true, isNested = true))
        items.add(ListItem("Ratings", it.games.rating.toString(), isNested = true))
        items.add(ListItem("Position", it.games.position.toString(), isNested = true))
        items.add(ListItem("Appearances", it.games.appearances.toString(), isNested = true))
        items.add(ListItem("Captain", it.games.captain.toString(), isNested = true))
        items.add(ListItem("Lineup", it.games.lineups.toString(), isNested = true))
        items.add(ListItem("Minutes", it.games.minutes.toString(), isNested = true))
        items.add(ListItem("Number", it.games.number.toString(), isNested = true))

        items.add(ListItem("Goals", "", true, isNested = true))
        items.add(ListItem("Assists", it.goals.assists.toString(), isNested = true))
        items.add(ListItem("Conceded", it.goals.conceded.toString(), isNested = true))
        items.add(ListItem("Saves", it.goals.saves.toString(), isNested = true))
        items.add(ListItem("Total", it.goals.total.toString(), isNested = true))

        items.add(ListItem("Passes", "", true, isNested = true))
        items.add(ListItem("Accuracy", it.passes.accuracy.toString(), isNested = true))
        items.add(ListItem("Key", it.passes.key.toString(), isNested = true))
        items.add(ListItem("Total", it.passes.total.toString(), isNested = true))

        items.add(ListItem("Cards", "", true, isNested = true))
        items.add(ListItem("Red", it.cards.red.toString(), isNested = true))
        items.add(ListItem("Yellow", it.cards.yellow.toString(), isNested = true))
        items.add(ListItem("Yellow + Red", it.cards.yellowred.toString(), isNested = true))

        items.add(ListItem("Dribbles", "", true, isNested = true))
        items.add(ListItem("Attempts", it.dribbles.attempts.toString(), isNested = true))
        items.add(ListItem("Past", it.dribbles.past.toString(), isNested = true))
        items.add(ListItem("Success", it.dribbles.success.toString(), isNested = true))

        items.add(ListItem("Duels", "", true, isNested = true))
        items.add(ListItem("Won", it.duels.won.toString(), isNested = true))
        items.add(ListItem("Total", it.duels.total.toString(), isNested = true))

        items.add(ListItem("Fouls", "", true, isNested = true))
        items.add(ListItem("Committed", it.fouls.committed.toString(), isNested = true))
        items.add(ListItem("Drawn", it.fouls.drawn.toString(), isNested = true))

        items.add(ListItem("Tackles", "", true, isNested = true))
        items.add(ListItem("Blocks", it.tackles.blocks.toString(), isNested = true))
        items.add(ListItem("Interceptions", it.tackles.interceptions.toString(), isNested = true))
        items.add(ListItem("Total", it.tackles.total.toString(), isNested = true))

        items.add(ListItem("Penalty", "", true, isNested = true))
        items.add(ListItem("Committed", it.penalty.commited.toString(), isNested = true))
        items.add(ListItem("Missed", it.penalty.missed.toString(), isNested = true))
        items.add(ListItem("Saved", it.penalty.saved.toString(), isNested = true))
        items.add(ListItem("Scored", it.penalty.scored.toString(), isNested = true))
        items.add(ListItem("Won", it.penalty.won.toString(), isNested = true))
    }
    val itemsNotNull = items.filter { it.isHeader || (!it.isHeader && !it.value.isNull()) }
    val test = itemsNotNull.filterIndexed { index, current ->
        index < itemsNotNull.size-1 && current.isHeader != itemsNotNull[index+1].isHeader
    }
    return test
}
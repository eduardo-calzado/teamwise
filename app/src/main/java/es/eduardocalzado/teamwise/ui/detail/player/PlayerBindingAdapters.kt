package es.eduardocalzado.teamwise.ui.detail.player

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.InfoItem
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
fun createPlayerAttributes(player: Player): List<InfoItem> {
    val items = mutableListOf<InfoItem>()
    items.add(InfoItem("Information", "", true))
    items.add(InfoItem("First Name", player.firstName.toString()))
    items.add(InfoItem("Last Name", player.lastName.toString()))
    items.add(InfoItem("Age", player.age.toString()))
    items.add(InfoItem("Height", player.height.toString()))
    items.add(InfoItem("Weight", player.weight.toString()))
    items.add(InfoItem("Nationality", player.nationality.toString()))
    items.add(InfoItem("Injured", player.injured.toString()))

    items.add(InfoItem("Team", "", true))
    items.add(InfoItem("Name", player.statistics.first().team.name.toString()))

    player.statistics.forEach {

        items.add(InfoItem("League", "", true))
        items.add(InfoItem("Name", it.league.name.toString()))

        items.add(InfoItem("Games", "", true, isNested = true))
        items.add(InfoItem("Ratings", it.games.rating.toString(), isNested = true))
        items.add(InfoItem("Position", it.games.position.toString(), isNested = true))
        items.add(InfoItem("Appearances", it.games.appearances.toString(), isNested = true))
        items.add(InfoItem("Captain", it.games.captain.toString(), isNested = true))
        items.add(InfoItem("Lineup", it.games.lineups.toString(), isNested = true))
        items.add(InfoItem("Minutes", it.games.minutes.toString(), isNested = true))
        items.add(InfoItem("Number", it.games.number.toString(), isNested = true))

        items.add(InfoItem("Goals", "", true, isNested = true))
        items.add(InfoItem("Assists", it.goals.assists.toString(), isNested = true))
        items.add(InfoItem("Conceded", it.goals.conceded.toString(), isNested = true))
        items.add(InfoItem("Saves", it.goals.saves.toString(), isNested = true))
        items.add(InfoItem("Total", it.goals.total.toString(), isNested = true))

        items.add(InfoItem("Passes", "", true, isNested = true))
        items.add(InfoItem("Accuracy", it.passes.accuracy.toString(), isNested = true))
        items.add(InfoItem("Key", it.passes.key.toString(), isNested = true))
        items.add(InfoItem("Total", it.passes.total.toString(), isNested = true))

        items.add(InfoItem("Cards", "", true, isNested = true))
        items.add(InfoItem("Red", it.cards.red.toString(), isNested = true))
        items.add(InfoItem("Yellow", it.cards.yellow.toString(), isNested = true))
        items.add(InfoItem("Yellow + Red", it.cards.yellowred.toString(), isNested = true))

        items.add(InfoItem("Dribbles", "", true, isNested = true))
        items.add(InfoItem("Attempts", it.dribbles.attempts.toString(), isNested = true))
        items.add(InfoItem("Past", it.dribbles.past.toString(), isNested = true))
        items.add(InfoItem("Success", it.dribbles.success.toString(), isNested = true))

        items.add(InfoItem("Duels", "", true, isNested = true))
        items.add(InfoItem("Won", it.duels.won.toString(), isNested = true))
        items.add(InfoItem("Total", it.duels.total.toString(), isNested = true))

        items.add(InfoItem("Fouls", "", true, isNested = true))
        items.add(InfoItem("Committed", it.fouls.committed.toString(), isNested = true))
        items.add(InfoItem("Drawn", it.fouls.drawn.toString(), isNested = true))

        items.add(InfoItem("Tackles", "", true, isNested = true))
        items.add(InfoItem("Blocks", it.tackles.blocks.toString(), isNested = true))
        items.add(InfoItem("Interceptions", it.tackles.interceptions.toString(), isNested = true))
        items.add(InfoItem("Total", it.tackles.total.toString(), isNested = true))

        items.add(InfoItem("Penalty", "", true, isNested = true))
        items.add(InfoItem("Committed", it.penalty.commited.toString(), isNested = true))
        items.add(InfoItem("Missed", it.penalty.missed.toString(), isNested = true))
        items.add(InfoItem("Saved", it.penalty.saved.toString(), isNested = true))
        items.add(InfoItem("Scored", it.penalty.scored.toString(), isNested = true))
        items.add(InfoItem("Won", it.penalty.won.toString(), isNested = true))
    }
    val itemsNotNull = items.filter { it.isHeader || (!it.isHeader && !it.value.isNull()) }
    val test = itemsNotNull.filterIndexed { index, current ->
        index < itemsNotNull.size-1 && current.isHeader != itemsNotNull[index+1].isHeader
    }
    return test
}
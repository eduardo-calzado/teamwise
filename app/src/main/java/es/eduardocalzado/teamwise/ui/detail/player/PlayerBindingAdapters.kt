package es.eduardocalzado.teamwise.ui.detail.player

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.PlayerInfoItem
import es.eduardocalzado.teamwise.framework.isNull

@BindingAdapter("info")
fun RecyclerView.setInfo(player: Player?) {
    if (player != null) {
        (adapter as? PlayerAdapter)?.submitList(createPlayerAttributes(player))
    }
}

/**
 * createPlayerAttributes. From a player with some values, creates a list of items.
 */
fun createPlayerAttributes(player: Player): List<PlayerInfoItem> {
    val items = mutableListOf<PlayerInfoItem>()
    items.add(PlayerInfoItem("Information", "", true))
    items.add(PlayerInfoItem("First Name", player.firstName.toString()))
    items.add(PlayerInfoItem("Last Name", player.lastName.toString()))
    items.add(PlayerInfoItem("Age", player.age.toString()))
    items.add(PlayerInfoItem("Height", player.height.toString()))
    items.add(PlayerInfoItem("Weight", player.weight.toString()))
    items.add(PlayerInfoItem("Nationality", player.nationality.toString()))
    items.add(PlayerInfoItem("Injured", player.injured.toString()))

    items.add(PlayerInfoItem("Team", "", true))
    items.add(PlayerInfoItem("Name", player.statistics.first().team.name.toString()))

    items.add(PlayerInfoItem("Statistics", "", true))
    items.add(PlayerInfoItem())


    player.statistics.forEach {

        items.add(PlayerInfoItem("League", "", true))
        items.add(PlayerInfoItem("Name", it.league.name.toString()))

        items.add(PlayerInfoItem("Games", "", true, isNested = true))
        items.add(PlayerInfoItem("Ratings", it.games.rating.toString(), isNested = true))
        items.add(PlayerInfoItem("Position", it.games.position.toString(), isNested = true))
        items.add(PlayerInfoItem("Appearances", it.games.appearances.toString(), isNested = true))
        items.add(PlayerInfoItem("Captain", it.games.captain.toString(), isNested = true))
        items.add(PlayerInfoItem("Lineup", it.games.lineups.toString(), isNested = true))
        items.add(PlayerInfoItem("Minutes", it.games.minutes.toString(), isNested = true))
        items.add(PlayerInfoItem("Number", it.games.number.toString(), isNested = true))

        items.add(PlayerInfoItem("Goals", "", true, isNested = true))
        items.add(PlayerInfoItem("Assists", it.goals.assists.toString(), isNested = true))
        items.add(PlayerInfoItem("Conceded", it.goals.conceded.toString(), isNested = true))
        items.add(PlayerInfoItem("Saves", it.goals.saves.toString(), isNested = true))
        items.add(PlayerInfoItem("Total", it.goals.total.toString(), isNested = true))

        items.add(PlayerInfoItem("Passes", "", true, isNested = true))
        items.add(PlayerInfoItem("Accuracy", it.passes.accuracy.toString(), isNested = true))
        items.add(PlayerInfoItem("Key", it.passes.key.toString(), isNested = true))
        items.add(PlayerInfoItem("Total", it.passes.total.toString(), isNested = true))

        items.add(PlayerInfoItem("Cards", "", true, isNested = true))
        items.add(PlayerInfoItem("Red", it.cards.red.toString(), isNested = true))
        items.add(PlayerInfoItem("Yellow", it.cards.yellow.toString(), isNested = true))
        items.add(PlayerInfoItem("Yellow + Red", it.cards.yellowred.toString(), isNested = true))

        items.add(PlayerInfoItem("Dribbles", "", true, isNested = true))
        items.add(PlayerInfoItem("Attempts", it.dribbles.attempts.toString(), isNested = true))
        items.add(PlayerInfoItem("Past", it.dribbles.past.toString(), isNested = true))
        items.add(PlayerInfoItem("Success", it.dribbles.success.toString(), isNested = true))

        items.add(PlayerInfoItem("Duels", "", true, isNested = true))
        items.add(PlayerInfoItem("Won", it.duels.won.toString(), isNested = true))
        items.add(PlayerInfoItem("Total", it.duels.total.toString(), isNested = true))

        items.add(PlayerInfoItem("Fouls", "", true, isNested = true))
        items.add(PlayerInfoItem("Committed", it.fouls.committed.toString(), isNested = true))
        items.add(PlayerInfoItem("Drawn", it.fouls.drawn.toString(), isNested = true))

        items.add(PlayerInfoItem("Tackles", "", true, isNested = true))
        items.add(PlayerInfoItem("Blocks", it.tackles.blocks.toString(), isNested = true))
        items.add(PlayerInfoItem("Interceptions", it.tackles.interceptions.toString(), isNested = true))
        items.add(PlayerInfoItem("Total", it.tackles.total.toString(), isNested = true))

        items.add(PlayerInfoItem("Penalty", "", true, isNested = true))
        items.add(PlayerInfoItem("Committed", it.penalty.commited.toString(), isNested = true))
        items.add(PlayerInfoItem("Missed", it.penalty.missed.toString(), isNested = true))
        items.add(PlayerInfoItem("Saved", it.penalty.saved.toString(), isNested = true))
        items.add(PlayerInfoItem("Scored", it.penalty.scored.toString(), isNested = true))
        items.add(PlayerInfoItem("Won", it.penalty.won.toString(), isNested = true))
    }
    val itemsNotNull = items.filter { it.isHeader || (!it.isHeader && !it.value.isNull()) }
    val test = itemsNotNull.filterIndexed { index, current ->
        index < itemsNotNull.size-1 && current.isHeader != itemsNotNull[index+1].isHeader
    }
    return test
}
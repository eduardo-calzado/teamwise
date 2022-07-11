package es.eduardocalzado.teamwise.ui.detail.players

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.Player

@BindingAdapter("players")
fun RecyclerView.setItems(players: List<Player>?) {
    if (players != null) {
        (adapter as? PlayersAdapter)?.submitList(players)
    }
}
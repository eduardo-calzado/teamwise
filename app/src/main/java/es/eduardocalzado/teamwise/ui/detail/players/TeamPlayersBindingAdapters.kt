package es.eduardocalzado.teamwise.ui.detail.players

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.TeamPlayer

@BindingAdapter("players")
fun RecyclerView.setItems(players: List<TeamPlayer>?) {
    if (players != null) {
        (adapter as? TeamPlayersAdapter)?.submitList(players)
    }
}
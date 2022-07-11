package es.eduardocalzado.teamwise.ui.detail.players

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.ViewPlayerItemBinding
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.ui.common.basicDiffUtil
import es.eduardocalzado.teamwise.ui.common.inflate

class PlayersAdapter(
    private val listener: (Player) -> Unit
): ListAdapter<Player, RecyclerView.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = parent.inflate(R.layout.view_player_item, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val player = getItem(position)
        (holder as PlayerViewHolder).bind(player)
        holder.itemView.setOnClickListener { listener(player) }
    }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewPlayerItemBinding.bind(view)
        fun bind(player: Player) {
            binding.player = player
        }
    }
}
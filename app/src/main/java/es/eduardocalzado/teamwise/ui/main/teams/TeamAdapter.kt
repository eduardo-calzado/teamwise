package es.eduardocalzado.teamwise.ui.main.teams

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.ViewTeamHeaderBinding
import es.eduardocalzado.teamwise.databinding.ViewTeamItemBinding
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.ui.common.basicDiffUtil
import es.eduardocalzado.teamwise.ui.common.inflate

private const val HEADER_VIEW_TYPE  = 0
private const val ITEM_VIEW_TYPE    = 1

class TeamAdapter(
    private val listener: (Team) -> Unit
): ListAdapter<Team, RecyclerView.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER_VIEW_TYPE) {
            val view = parent.inflate(R.layout.view_team_header, false)
            return HeaderViewHolder(view)
        }
        val view = parent.inflate(R.layout.view_team_item, false)
        return ItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        // return if (position == 0) {
        //     HEADER_VIEW_TYPE
        // } else {
            return ITEM_VIEW_TYPE
        // }
    }

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {
        if (holderItem is HeaderViewHolder) {
            holderItem.bind(currentList)
        }
        if (holderItem is ItemViewHolder) {
            val team = getItem(position)
            holderItem.bind(team)
            holderItem.itemView.setOnClickListener { listener(team) }
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewTeamItemBinding.bind(view)
        fun bind(team: Team) {
            binding.team = team
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewTeamHeaderBinding.bind(view)
        fun bind(teams: List<Team>) {
            binding.teams = teams
        }
    }
}
package es.eduardocalzado.teamwise.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.ViewTeamBinding
import es.eduardocalzado.teamwise.model.database.Team
import es.eduardocalzado.teamwise.model.extensions.basicDiffUtil
import es.eduardocalzado.teamwise.model.extensions.inflate
import es.eduardocalzado.teamwise.model.extensions.loadUrl

class TeamAdapter(
    private val listener: (Team) -> Unit
): ListAdapter<Team, TeamAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_team, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = getItem(position)
        holder.bind(team)
        holder.itemView.setOnClickListener { listener(team) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewTeamBinding.bind(view)
        fun bind(team: Team) {
            binding.team = team
        }
    }
}
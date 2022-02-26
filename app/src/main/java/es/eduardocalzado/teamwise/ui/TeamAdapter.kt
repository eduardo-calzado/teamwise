package es.eduardocalzado.teamwise.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.ViewTeamBinding
import es.eduardocalzado.teamwise.extensions.inflate
import es.eduardocalzado.teamwise.extensions.loadUrl

class TeamAdapter(
    var teams: List<Team>
    ) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_team, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewTeamBinding.bind(view)
        fun bind(team: Team) = with(binding) {
            teamName.text = team.details.name
            teamCover.loadUrl(team.details.logo)
        }
    }
}
package es.eduardocalzado.teamwise.domain

import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.network.TeamRepository

class SwitchTeamFavoriteUseCase (private val repository: TeamRepository){
    suspend operator fun invoke(team: Team) = repository.switchFavorite(team)
}
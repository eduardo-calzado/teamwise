package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.data.network.TeamRepository

class SwitchTeamFavoriteUseCase (private val repository: TeamRepository){
    suspend operator fun invoke(team: Team) = repository.switchFavorite(team)
}
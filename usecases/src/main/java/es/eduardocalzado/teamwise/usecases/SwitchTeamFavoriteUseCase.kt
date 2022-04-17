package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.data.TeamRepository
import javax.inject.Inject

class SwitchTeamFavoriteUseCase @Inject constructor(private val repository: TeamRepository){
    suspend operator fun invoke(team: Team) = repository.switchFavorite(team)
}
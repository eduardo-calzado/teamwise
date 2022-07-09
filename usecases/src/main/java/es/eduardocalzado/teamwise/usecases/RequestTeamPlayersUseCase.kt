package es.eduardocalzado.teamwise.usecases

import arrow.core.Either
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.TeamPlayer
import javax.inject.Inject

class RequestTeamPlayersUseCase @Inject constructor(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(team: Int, season: Int): Either<Error, List<TeamPlayer>> = teamRepository.requestTeamPlayers(team, season)
}
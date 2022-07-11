package es.eduardocalzado.teamwise.usecases

import arrow.core.Either
import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import javax.inject.Inject

class RequestPlayersByTeamUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(team: Int, season: Int): Error?  = playerRepository.requestPlayersByTeam(team, season)
}
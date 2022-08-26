package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.domain.Error
import javax.inject.Inject

class RequestPlayersByTeamUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(team: Int, season: Int): Error?  = playerRepository.requestPlayersByTeam(team, season)
}
package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.errors.Error
import es.eduardocalzado.teamwise.data.network.TeamRepository

class RequestTeamsUseCase (private val teamRepository: TeamRepository) {
    suspend operator fun invoke(): Error? = teamRepository.requestTeams()
}
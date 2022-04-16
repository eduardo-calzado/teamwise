package es.eduardocalzado.teamwise.domain

import es.eduardocalzado.teamwise.data.errors.Error
import es.eduardocalzado.teamwise.data.network.TeamRepository

class RequestTeamsUseCase (private val teamRepository: TeamRepository) {
    suspend operator fun invoke(): Error? = teamRepository.requestTeams()
}
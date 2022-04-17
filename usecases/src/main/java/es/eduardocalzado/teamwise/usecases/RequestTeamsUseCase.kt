package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Error
import javax.inject.Inject

class RequestTeamsUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    suspend operator fun invoke(): Error? = teamRepository.requestTeams()
}
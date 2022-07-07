package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTeamsUseCase @Inject constructor (private val teamRepository: TeamRepository) {
    suspend operator fun invoke() = teamRepository.deleteTeams()
}
package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.data.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor (private val teamRepository: TeamRepository) {
    operator fun invoke(): Flow<List<Team>> = teamRepository.teams
}
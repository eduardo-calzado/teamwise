package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.data.network.TeamRepository
import kotlinx.coroutines.flow.Flow

class GetTeamsUseCase (private val teamRepository: TeamRepository) {
    operator fun invoke(): Flow<List<Team>> = teamRepository.teams
}
package es.eduardocalzado.teamwise.domain

import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.network.TeamRepository
import kotlinx.coroutines.flow.Flow

class GetTeamsUseCase (private val teamRepository: TeamRepository) {
    operator fun invoke(): Flow<List<Team>> = teamRepository.teams
}
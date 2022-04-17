package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.domain.Team
import arrow.core.Either
import es.eduardocalzado.teamwise.domain.Error

interface TeamRemoteDataSource {
    // suspend fun getTeams(): RemoteTeamsData
    // suspend fun getTeamStats(team: Int): RemoteTeamStatsData
    suspend fun getTeamsByRegion(region: String) : Either<Error, List<Team>>
}
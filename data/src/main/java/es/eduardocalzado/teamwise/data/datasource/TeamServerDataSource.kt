package es.eduardocalzado.teamwise.data.datasource

import arrow.core.Either
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.TeamStats

interface TeamRemoteDataSource {
    suspend fun getTeamStats(league: Int, season: Int, team: Int): Either<Error, TeamStats>
    suspend fun getTeamsByRegion(region: String) : Either<Error, List<Team>>
    suspend fun getTeams(country: String, league: Int, season: Int) : Either<Error, List<Team>>
}
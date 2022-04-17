package es.eduardocalzado.teamwise.framework.server

import arrow.core.Either
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.framework.tryCall
import javax.inject.Inject

class TeamServerDataSource @Inject constructor () : TeamRemoteDataSource {
    // override suspend fun getTeams() = RemoteConnection.service.getTeams(Constants.LEAGUE, Constants.SEASON)

    override suspend fun getTeamsByRegion(region: String) : Either<Error, List<Team>> = tryCall {
        RemoteConnection
            .service
            .getTeams(region)
            .teams
            .toDomainModel()
    }

    /* override suspend fun getTeamStats(team: Int) =
        RemoteConnection
            .service
            .getTeamStats(Constants.LEAGUE, Constants.SEASON, team) */
}

// #MARK: toDomainModel
private fun List<RemoteTeam>.toDomainModel(): List<Team> = map { it.toDomainModel() }
private fun RemoteTeam.toDomainModel(): Team =
    Team(
        details.id,
        details.name,
        details.code,
        details.country,
        details.founded,
        details.national,
        details.logo,
        venue.address,
        venue.city,
        venue.capacity,
        venue.surface,
        venue.image ?: "",
        false,
    )
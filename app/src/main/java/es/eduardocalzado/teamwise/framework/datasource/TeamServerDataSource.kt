package es.eduardocalzado.teamwise.framework.datasource

import es.eduardocalzado.teamwise.data.constants.Constants
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.data.network.RemoteConnection
import es.eduardocalzado.teamwise.data.remotedata.RemoteTeam
import es.eduardocalzado.teamwise.domain.Team


class TeamServerDataSource() : TeamRemoteDataSource {
    override suspend fun getTeams() = RemoteConnection.service.getTeams(Constants.LEAGUE, Constants.SEASON)

    override suspend fun getTeamsByRegion(region: String) : List<Team> =
        RemoteConnection
            .service
            .getTeams(region)
            .teams
            .toDomainModel()

    override suspend fun getTeamStats(team: Int) =
        RemoteConnection
            .service
            .getTeamStats(Constants.LEAGUE, Constants.SEASON, team)
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
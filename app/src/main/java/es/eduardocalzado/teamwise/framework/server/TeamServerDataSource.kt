package es.eduardocalzado.teamwise.framework.server

import arrow.core.Either
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.TeamStats
import es.eduardocalzado.teamwise.framework.tryCall
import javax.inject.Inject

class TeamServerDataSource @Inject constructor () : TeamRemoteDataSource {
    /**
     * getTeamsByRegion. Get the teams list for a specific region (England by default)
     */
    override suspend fun getTeamsByRegion(region: String) : Either<Error, List<Team>> = tryCall {
        RemoteConnection
            .service
            .getTeamsByRegion(region)
            .teams
            .toDomainModel()
    }

    /**
     * getTeamsByRegion. Get the teams list for a specific country, league and season.
     */
    override suspend fun getTeams(country: String, league: Int, season: Int): Either<Error, List<Team>> = tryCall {
        RemoteConnection
            .service
            .getTeams(country, league, season)
            .teams
            .toDomainModel()
    }

    /**
     * getTeamStats. Get the team statistics for a specific team.
     */
    override suspend fun getTeamStats(league: Int, season: Int, team: Int) :Either<Error, TeamStats> = tryCall {
        RemoteConnection
            .service
            .getTeamStats(league, season, team)
            .stats
            .toDomainModel()
    }

    // override suspend fun getTeams() = RemoteConnection.service.getTeams(Constants.LEAGUE, Constants.SEASON)
}

// #MARK: RemoteTeam.toDomainModel
@JvmName("toDomainModelRemoteTeam")
private fun List<RemoteTeam>.toDomainModel(): List<Team> = map { it.toDomainModel() }
private fun RemoteTeam.toDomainModel(): Team =
    Team(
        id = details.id,
        name = details.name,
        code = details.code,
        country = details.country,
        founded = details.founded,
        national = details.national,
        logo = details.logo,
        address = venue.address,
        city = venue.city,
        capacity = venue.capacity,
        surface = venue.surface,
        stadiumImage = venue.image ?: "",
        stadiumName = venue.name ?: "",
        favorite = false,
    )

// #MARK: RemoteTeamStats.toDomainModel
private fun RemoteTeamStats.toDomainModel(): TeamStats =
    TeamStats(
        id = team.id,
        league_name = league.name,
        league_country = league.country,
        fixture_draws_total =fixtures.draws.total,
        fixture_loses_total =fixtures.loses.total,
        fixture_wins_total =fixtures.wins.total,
    )
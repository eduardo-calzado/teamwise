package es.eduardocalzado.teamwise.framework.server

import arrow.core.Either
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.*
import es.eduardocalzado.teamwise.framework.tryCall
import javax.inject.Inject
import kotlin.math.log

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
    override suspend fun getTeamStats(league: Int, season: Int, team: Int) :Either<Error, Team.Stats> = tryCall {
        RemoteConnection
            .service
            .getTeamStats(league, season, team)
            .stats
            .toDomainModel()
    }
}

// #MARK: RemoteTeams.Team.toDomainModel
@JvmName("toDomainModelRemoteTeamsTeam")
private fun List<RemoteTeams.Team>.toDomainModel(): List<Team> = map { it.toDomainModel() }
private fun RemoteTeams.Team.toDomainModel(): Team =
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
        emptyList(),
    )

// #MARK: RemoteTeamStats.Stat.toDomainModel
private fun RemoteTeamStats.Stat.toDomainModel(): Team.Stats =
    Team.Stats(
        id = team.id,
        league = Team.Stats.League(
            id = league.id,
            name = league.name,
            country = league.country,
            logo = league.logo,
            flag = league.flag,
            season = league.season,
        ),
        form = form,
        fixtures = Team.Stats.Fixtures(
            played = Team.Stats.Fixtures.InnerData(fixtures.played.home, fixtures.played.away, fixtures.played.total),
            wins = Team.Stats.Fixtures.InnerData(fixtures.wins.home, fixtures.wins.away, fixtures.wins.total),
            draws = Team.Stats.Fixtures.InnerData(fixtures.draws.home, fixtures.draws.away, fixtures.draws.total),
            loses = Team.Stats.Fixtures.InnerData(fixtures.loses.home, fixtures.loses.away, fixtures.loses.total),
        ),
        biggest = Team.Stats.Biggest(
            streak =  Team.Stats.Biggest.StreakData(biggest.streak.wins, biggest.streak.draws, biggest.streak.loses),
            wins =  Team.Stats.Biggest.HomeAwayData(biggest.wins.home, biggest.wins.away),
            loses =  Team.Stats.Biggest.HomeAwayData(biggest.loses.home, biggest.loses.away),
        ),
        cleanSheet = Team.Stats.CleanSheet(
            home = cleanSheet.home,
            away = cleanSheet.away,
            total = cleanSheet.total,
        )
    )
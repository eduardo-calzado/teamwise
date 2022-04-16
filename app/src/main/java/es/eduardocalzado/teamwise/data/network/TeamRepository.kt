package es.eduardocalzado.teamwise.data.network

import es.eduardocalzado.teamwise.App
import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.data.errors.Error
import es.eduardocalzado.teamwise.data.errors.tryCall
import es.eduardocalzado.teamwise.data.remotedata.RemoteTeam
import es.eduardocalzado.teamwise.data.remotedata.RemoteTeamStatsData

class TeamRepository(application: App) {

    private val regionRepository = RegionRepository(application)

    private val localDataSource = TeamLocalDataSource(
        teamDao = application.db.teamDao()
    )
    private val remoteDataSource = TeamRemoteDataSource()

    val teams = localDataSource.teams

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun requestTeams(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val data = remoteDataSource.getTeamsByRegion(regionRepository)
            localDataSource.save(data.teams.map {it.toLocalModel()})
        }
    }

    suspend fun requestStats(id: Int): RemoteTeamStatsData {
        return remoteDataSource.getTeamStats(id)
    }

    suspend fun switchFavorite(team: Team) = tryCall {
        val updatedTeam = team.copy(favorite = !team.favorite)
        localDataSource.save(listOf(updatedTeam))
    }
}

private fun RemoteTeam.toLocalModel(): Team = Team(
    details.id,
    details.name,
    details.code ?: "",
    details.country,
    details.founded,
    details.national,
    details.logo,
    venue.address ?: "",
    venue.city ?: "",
    venue.capacity,
    venue.surface ?: "",
    venue.image ?: "",
    false,
)
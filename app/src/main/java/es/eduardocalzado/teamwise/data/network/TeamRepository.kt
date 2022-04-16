package es.eduardocalzado.teamwise.data.network

import es.eduardocalzado.teamwise.App
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.errors.Error
import es.eduardocalzado.teamwise.data.errors.tryCall
import es.eduardocalzado.teamwise.data.remotedata.RemoteTeamStatsData
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.framework.datasource.TeamRoomDataSource
import es.eduardocalzado.teamwise.framework.datasource.TeamServerDataSource

class TeamRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource: TeamLocalDataSource = TeamRoomDataSource(application.db.teamDao())
    private val remoteDataSource: TeamServerDataSource = TeamServerDataSource()

    val teams = localDataSource.teams

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun requestTeams(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val data = remoteDataSource.getTeamsByRegion(regionRepository.findLastRegion())
            localDataSource.save(data)
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
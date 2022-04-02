package es.eduardocalzado.teamwise.model.network

import android.util.Log
import es.eduardocalzado.teamwise.App
import es.eduardocalzado.teamwise.model.database.Team
import es.eduardocalzado.teamwise.model.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.model.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.model.remotedata.RemoteTeam
import es.eduardocalzado.teamwise.model.remotedata.RemoteTeamStatsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRepository(application: App) {

    private val regionRepository = RegionRepository(application)

    private val localDataSource = TeamLocalDataSource(
        teamDao = application.db.teamDao()
    )
    private val remoteDataSource = TeamRemoteDataSource()

    val teams = localDataSource.teams

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun requestTeams() {
        if (localDataSource.isEmpty()) {
            val data = remoteDataSource.getTeamsByRegion(regionRepository)
            localDataSource.save(data.teams.map {it.toLocalModel()})
        }
    }

    suspend fun requestStats(id: Int): RemoteTeamStatsData {
        return remoteDataSource.getTeamStats(id)
    }

    suspend fun switchFavorite(team: Team) {
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
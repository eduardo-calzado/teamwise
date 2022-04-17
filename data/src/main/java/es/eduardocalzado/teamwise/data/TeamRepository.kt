package es.eduardocalzado.teamwise.data

import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: TeamLocalDataSource,
    private val remoteDataSource: TeamRemoteDataSource,
) {
    val teams = localDataSource.teams

    fun findById(id: Int) : Flow<Team> = localDataSource.findById(id)

    suspend fun requestTeams(): Error? {
        if (localDataSource.isEmpty()) {
            val teamData = remoteDataSource.getTeamsByRegion(regionRepository.findLastRegion())
            teamData.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(team: Team) : Error? {
        val updatedTeam = team.copy(favorite = !team.favorite)
        return localDataSource.save(listOf(updatedTeam))
    }
}
package es.eduardocalzado.teamwise.model.datasource

import es.eduardocalzado.teamwise.model.constants.Constants
import es.eduardocalzado.teamwise.model.network.APIFootballConnection
import es.eduardocalzado.teamwise.model.network.RegionRepository

class TeamRemoteDataSource(
    private val regionRepository: RegionRepository
) {

    suspend fun getTeams() = APIFootballConnection.service.getTeams(Constants.LEAGUE, Constants.SEASON)
    suspend fun getTeamsByRegion() = APIFootballConnection.service.getTeams(regionRepository.findLastRegion())
    suspend fun getTeamStats(team: Int) = APIFootballConnection.service.getTeamStats(Constants.LEAGUE, Constants.SEASON, team)
}
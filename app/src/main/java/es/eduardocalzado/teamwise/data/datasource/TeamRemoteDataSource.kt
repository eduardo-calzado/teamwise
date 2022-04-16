package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.data.constants.Constants
import es.eduardocalzado.teamwise.data.network.APIFootballConnection
import es.eduardocalzado.teamwise.data.network.RegionRepository

class TeamRemoteDataSource() {
    suspend fun getTeams() = APIFootballConnection.service.getTeams(Constants.LEAGUE, Constants.SEASON)
    suspend fun getTeamsByRegion(region: RegionRepository) = APIFootballConnection.service.getTeams(region.findLastRegion())
    suspend fun getTeamStats(team: Int) = APIFootballConnection.service.getTeamStats(Constants.LEAGUE, Constants.SEASON, team)
}
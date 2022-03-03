package es.eduardocalzado.teamwise.model.network

import androidx.appcompat.app.AppCompatActivity
import es.eduardocalzado.teamwise.model.constants.Constants

class TeamRepository(activity: AppCompatActivity) {

    private val regionRepository = RegionRepository(activity)

    suspend fun getTeams() = APIFootballConnection.service.getTeams(Constants.LEAGUE, Constants.SEASON)
    suspend fun getTeamsByRegion() = APIFootballConnection.service.getTeams(regionRepository.findLastRegion())
    suspend fun getTeamStats(team: Int) = APIFootballConnection.service.getTeamStats(Constants.LEAGUE, Constants.SEASON, team)
}
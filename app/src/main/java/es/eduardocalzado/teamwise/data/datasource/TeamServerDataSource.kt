package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.data.remotedata.RemoteTeamStatsData
import es.eduardocalzado.teamwise.data.remotedata.RemoteTeamsData
import es.eduardocalzado.teamwise.domain.Team

interface TeamRemoteDataSource {
    suspend fun getTeams(): RemoteTeamsData

    suspend fun getTeamsByRegion(region: String) : List<Team>

    suspend fun getTeamStats(team: Int): RemoteTeamStatsData
}
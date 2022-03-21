package es.eduardocalzado.teamwise.model.network

import es.eduardocalzado.teamwise.model.remotedata.RemoteTeamStatsData
import es.eduardocalzado.teamwise.model.remotedata.RemoteTeamsData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIFootball {

    companion object {
        const val HOST: String = "x-rapidapi-host: api-football-v1.p.rapidapi.com"
        const val KEY: String = "x-rapidapi-key: 4e0a1fb7f4msh50bc0de4a5828d2p149427jsn5ce86752dbee"
    }

    @Headers(HOST, KEY)
    @GET("teams")
    suspend fun getTeams(
        @Query("league") league: Int,
        @Query("season") season: Int,
    ): RemoteTeamsData

    @Headers(HOST, KEY)
    @GET("teams")
    suspend fun getTeams(
        @Query("country") country: String,
    ): RemoteTeamsData

    @Headers(HOST, KEY)
    @GET("teams/statistics")
    suspend fun getTeamStats(
        @Query("league") league: Int,
        @Query("season") season: Int,
        @Query("team") team: Int,
    ): RemoteTeamStatsData

}
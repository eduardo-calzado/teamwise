package es.eduardocalzado.teamwise.framework.server

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RemoteService {

    companion object {
        const val HOST: String = "x-rapidapi-host: api-football-v1.p.rapidapi.com"
        const val KEY: String = "x-rapidapi-key: 4e0a1fb7f4msh50bc0de4a5828d2p149427jsn5ce86752dbee"
    }

    @Headers(HOST, KEY)
    @GET("teams")
    suspend fun getTeams(
        @Query("country") country: String,
        @Query("league") league: Int,
        @Query("season") season: Int,
    ): RemoteTeams

    @Headers(HOST, KEY)
    @GET("teams")
    suspend fun getTeamsByRegion(
        @Query("country") country: String,
    ): RemoteTeams

    @Headers(HOST, KEY)
    @GET("teams/statistics")
    suspend fun getTeamStats(
        @Query("league") league: Int,
        @Query("season") season: Int,
        @Query("team") team: Int,
    ): RemoteTeamStats

    @Headers(HOST, KEY)
    @GET("players")
    suspend fun getPlayersByTeam(
        @Query("team") player: Int,
        @Query("season") season: Int,
    ): RemotePlayers

}
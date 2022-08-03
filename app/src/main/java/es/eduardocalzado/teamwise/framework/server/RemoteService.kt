package es.eduardocalzado.teamwise.framework.server

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RemoteService {

    companion object {
        const val HOST: String = "x-rapidapi-host: api-football-v1.p.rapidapi.com"
        const val KEY: String = "x-rapidapi-key: 8a9e84d8b7msh20b23cf9bbf1058p1f4055jsna3775d7b584a"
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
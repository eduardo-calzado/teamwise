package es.eduardocalzado.teamwise.model

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RemoteService {

    @Headers(
        "x-rapidapi-host: api-football-v1.p.rapidapi.com",
        "x-rapidapi-key: 4e0a1fb7f4msh50bc0de4a5828d2p149427jsn5ce86752dbee"
    )
    @GET("teams?league=2&season=2021")
    suspend fun getTeams(
        //@Query("league") league: Int,
        //@Query("season") season: Int,
    ): RemoteResult

}
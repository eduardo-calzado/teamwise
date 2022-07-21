package es.eduardocalzado.teamwise.framework.server

import com.google.gson.annotations.SerializedName

data class RemoteTeamStats(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response") val stats: Stat
) {
    data class Stat(
        val team: Team,
        val league: League,
        val fixtures: Fixtures,
    )
    {
        data class Team(
            val id: Int,
            val name: String,
            val logo: String,
        )

        data class League(
            val name: String,
            val country: String,
            val logo: String,
            val flag: String,
        )

        data class FixturesData(
            val home: Int,
            val away: Int,
            val total: Int,
        )

        data class Fixtures(
            val played: FixturesData,
            val wins: FixturesData,
            val draws: FixturesData,
            val loses: FixturesData,
        )
    }
}
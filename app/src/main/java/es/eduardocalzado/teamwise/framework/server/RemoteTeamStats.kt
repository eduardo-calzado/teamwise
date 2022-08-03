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
        val form: String,
        val fixtures: Fixtures,
        val biggest: Biggest,
        @SerializedName("clean_sheet") val cleanSheet: CleanSheet,
    )
    {
        data class Team(
            val id: Int,
            val name: String,
            val logo: String,
        )
        data class League(
            val id: Int,
            val name: String,
            val country: String,
            val logo: String,
            val flag: String,
            val season: Int,
        )
        data class Fixtures(
            val played: InnerData,
            val wins: InnerData,
            val draws: InnerData,
            val loses: InnerData,
        ) {
            data class InnerData(
                val home: Int,
                val away: Int,
                val total: Int,
            )
        }
        data class Biggest(
            val streak: StreakData,
            val wins: HomeAwayData,
            val loses: HomeAwayData,
        ) {
            data class StreakData(
                val wins: Int,
                val draws: Int,
                val loses: Int,
            )
            data class HomeAwayData(
                val home: String,
                val away: String,
            )
        }
        data class CleanSheet(
            val home: Int,
            val away: Int,
            val total: Int,
        )
    }
}
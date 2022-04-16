package es.eduardocalzado.teamwise.data.remotedata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RemoteTeamStatsData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response") val stats: RemoteTeamStats
)

data class RemoteTeamStats(
    val league: RemoteTeamStatsLeague,
    val fixtures: RemoteTeamStatsFixtures,
)

data class RemoteTeamStatsLeague(
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
)

data class RemoteTeamStatsFixtures(
    val played: RemoteTeamStatsFixturesData,
    val wins: RemoteTeamStatsFixturesData,
    val draws: RemoteTeamStatsFixturesData,
    val loses: RemoteTeamStatsFixturesData,
)

data class RemoteTeamStatsFixturesData(
    val home: Int,
    val away: Int,
    val total: Int,
)
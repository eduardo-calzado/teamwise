package es.eduardocalzado.teamwise.model.remotedata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RemoteTeamStatsData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response") val stats: RemoteTeamStats
)

@Parcelize
data class RemoteTeamStats(
    val league: RemoteTeamStatsLeague,
    val fixtures: RemoteTeamStatsFixtures,
) : Parcelable

@Parcelize
data class RemoteTeamStatsLeague(
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
) : Parcelable

@Parcelize
data class RemoteTeamStatsFixtures(
    val played: RemoteTeamStatsFixturesData,
    val wins: RemoteTeamStatsFixturesData,
    val draws: RemoteTeamStatsFixturesData,
    val loses: RemoteTeamStatsFixturesData,
) : Parcelable

@Parcelize
data class RemoteTeamStatsFixturesData(
    val home: Int,
    val away: Int,
    val total: Int,
) : Parcelable
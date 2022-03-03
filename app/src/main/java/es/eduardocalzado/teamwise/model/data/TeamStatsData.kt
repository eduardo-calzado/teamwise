package es.eduardocalzado.teamwise.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TeamStatsData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response") val stats: TeamStats
)

@Parcelize
data class TeamStats(
    val league: TeamStatsLeague,
    val fixtures: TeamStatsFixtures,
) : Parcelable

@Parcelize
data class TeamStatsLeague(
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
) : Parcelable

@Parcelize
data class TeamStatsFixtures(
    val played: TeamStatsFixturesData,
    val wins: TeamStatsFixturesData,
    val draws: TeamStatsFixturesData,
    val loses: TeamStatsFixturesData,
) : Parcelable

@Parcelize
data class TeamStatsFixturesData(
    val home: Int,
    val away: Int,
    val total: Int,
) : Parcelable
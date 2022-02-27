package es.eduardocalzado.teamwise.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamsData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response") val teams: List<Team>
): Parcelable

@Parcelize
data class Team(
    @SerializedName("team") val details: TeamDetails,
    val venue: TeamVenue
): Parcelable

@Parcelize
data class TeamDetails(
    val id: Int,
    val name: String,
    val code: String,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String,
) : Parcelable

@Parcelize
data class TeamVenue(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val capacity: Int,
    val surface: String,
    val image: String,
) : Parcelable
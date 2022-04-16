package es.eduardocalzado.teamwise.data.remotedata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoteTeamsData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response")
    val teams: List<RemoteTeam>
): Parcelable

@Parcelize
data class RemoteTeam(
    @SerializedName("team")
    val details: RemoteTeamDetails,
    val venue: RemoteTeamVenue
): Parcelable

@Parcelize
data class RemoteTeamDetails(
    val id: Int,
    val name: String,
    val code: String?,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String,
) : Parcelable

@Parcelize
data class RemoteTeamVenue(
    val id: Int,
    val name: String,
    val address: String?,
    val city: String?,
    val capacity: Int,
    val surface: String?,
    val image: String?,
) : Parcelable
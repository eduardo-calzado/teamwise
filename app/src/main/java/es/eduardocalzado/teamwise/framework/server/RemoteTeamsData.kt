package es.eduardocalzado.teamwise.framework.server

import com.google.gson.annotations.SerializedName

data class RemoteTeamsData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response")
    val teams: List<RemoteTeam>
)

data class RemoteTeam(
    @SerializedName("team")
    val details: RemoteTeamDetails,
    val venue: RemoteTeamVenue
)

data class RemoteTeamDetails(
    val id: Int,
    val name: String,
    val code: String?,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String,
)

data class RemoteTeamVenue(
    val id: Int,
    val name: String,
    val address: String?,
    val city: String?,
    val capacity: Int,
    val surface: String?,
    val image: String?,
)
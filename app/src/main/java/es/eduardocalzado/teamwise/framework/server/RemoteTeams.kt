package es.eduardocalzado.teamwise.framework.server

import com.google.gson.annotations.SerializedName

data class RemoteTeams(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response")
    val teams: List<Team>
) {
    data class Team(
        @SerializedName("team")
        val details: Details,
        val venue: Venue,
        val league: Int?,
    ) {
        data class Details(
            val id: Int,
            val name: String,
            val code: String?,
            val country: String,
            val founded: Int,
            val national: Boolean,
            val logo: String,
        )
        data class Venue(
            val id: Int,
            val name: String?,
            val address: String?,
            val city: String?,
            val capacity: Int,
            val surface: String?,
            val image: String?,
        )
    }
}
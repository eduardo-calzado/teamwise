package es.eduardocalzado.teamwise.framework.server

import com.google.gson.annotations.SerializedName

data class RemoteTeamPlayersData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response")
    val players: List<RemoteTeamPlayer>
)

data class RemoteTeamPlayer(
    val player: RemoteTeamPlayerInfo,
)

data class RemoteTeamPlayerInfo(
    val id: Int,
    val name: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val nationality: String,
    val height: String,
    val weight: String,
    val injured: Boolean,
    val photo: String
)
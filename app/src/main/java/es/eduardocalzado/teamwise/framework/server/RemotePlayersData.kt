package es.eduardocalzado.teamwise.framework.server

import com.google.gson.annotations.SerializedName

data class RemotePlayersData(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response")
    val players: List<RemotePlayer>
)

data class RemotePlayer(
    val player: RemotePlayerInfo,
)

data class RemotePlayerInfo(
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
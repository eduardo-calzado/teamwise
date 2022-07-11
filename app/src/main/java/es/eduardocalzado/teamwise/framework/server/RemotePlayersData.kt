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
    @SerializedName("statistics")
    val stats: List<RemotePlayerInfoData>,
)

data class RemotePlayerInfo(
    val id: Int,
    val name: String,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    val age: Int,
    val nationality: String,
    val height: String,
    val weight: String,
    val injured: Boolean,
    val photo: String
)

data class RemotePlayerInfoData(
    val team: RemotePlayerInfoTeam,
    val league: RemotePlayerInfoLeague,
    val games: RemotePlayerInfoGames,
)

data class RemotePlayerInfoTeam (
    val id: Int,
    val name: String,
    val logo: String,
)

data class RemotePlayerInfoLeague (
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
    val season: Int,
)

data class RemotePlayerInfoGames (
    val appearences: Int,
    val lineups: Int,
    val minutes: Int,
    val number: Int?,
    val position: String,
    val rating: String,
    val captain: Boolean,
)
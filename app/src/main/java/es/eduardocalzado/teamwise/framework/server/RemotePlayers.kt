package es.eduardocalzado.teamwise.framework.server

import com.google.gson.annotations.SerializedName

data class RemotePlayers(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response")
    val players: List<Players>
) {
    data class Players(
        val player: Player,
        val statistics: List<Stats>,
    ) {
        data class Player(
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

        data class Stats(
            val team: Team,
            val league: League,
            val games: Games,
            val goals: Goals,
            val passes: Passes,
            val tackles: Tackles,
            val duels: Duels,
            val dribbles: Dribbles,
            val fouls: Fouls,
            val cards: Cards,
            val penalty: Penalty,
        ) {
            data class Team(
                val id: Int,
                val name: String,
                val logo: String,
            )

            data class League(
                val id: Int,
                val name: String,
                val country: String,
                val logo: String,
                val flag: String,
                val season: Int,
            )

            data class Games(
                val appearences: Int,
                val lineups: Int,
                val minutes: Int,
                val number: Int?,
                val position: String,
                val rating: String,
                val captain: Boolean,
            )

            data class Goals(
                val total: Int?,
                val conceded: Int?,
                val assists: Int?,
                val saves: Int?
            )

            data class Passes(
                val total: Int?,
                val key: Int?,
                val accuracy: Int?,
            )

            data class Tackles(
                val total: Int?,
                val blocks: Int?,
                val interceptions: Int?,
            )

            data class Duels(
                val total: Int?,
                val won: Int?,
            )

            data class Dribbles(
                val attempts: Int?,
                val success: Int?,
                val past: Int?,
            )

            data class Fouls(
                val drawn: Int?,
                val committed: Int?,
            )

            data class Cards(
                val yellow: Int?,
                val yellowred: Int?,
                val red: Int?,
            )

            data class Penalty(
                val won: Int?,
                val commited: Int?,
                val scored: Int?,
                val missed: Int?,
                val saved: Int?,
            )
        }
    }
}
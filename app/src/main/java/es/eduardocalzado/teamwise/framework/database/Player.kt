package es.eduardocalzado.teamwise.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.eduardocalzado.teamwise.domain.Player

@Entity
data class Player (
    @PrimaryKey val id: Int,
    val name: String,
    val firstName: String?,
    val lastName: String?,
    val age: Int,
    val nationality: String,
    val height: String?,
    val weight: String?,
    val injured: Boolean,
    val photo: String,
    val team: Int,
    @TypeConverters(Converters::class)
    val statistics: List<Stats>,
) {
    @Entity(tableName = "PlayerStats")
    data class Stats(
        @TypeConverters(Converters::class)
        val team: Team,
        @TypeConverters(Converters::class)
        val league: League,
        @TypeConverters(Converters::class)
        val games: Games,
        @TypeConverters(Converters::class)
        val goals: Goals,
        @TypeConverters(Converters::class)
        val passes: Passes,
        @TypeConverters(Converters::class)
        val tackles: Tackles,
        @TypeConverters(Converters::class)
        val duels: Duels,
        @TypeConverters(Converters::class)
        val dribbles: Dribbles,
        @TypeConverters(Converters::class)
        val fouls: Fouls,
        @TypeConverters(Converters::class)
        val cards: Cards,
        @TypeConverters(Converters::class)
        val penalty: Penalty,
    ) {
        @Entity(tableName = "PlayerStatsTeam")
        data class Team(
            @PrimaryKey val id: Int,
            val name: String?,
            val logo: String?
        )
        @Entity(tableName = "PlayerStatsLeague")
        data class League(
            @PrimaryKey val id: Int,
            val name: String?,
            val country: String?,
            val logo: String?,
            val flag: String?,
            val season: Int?
        )
        @Entity(tableName = "PlayerStatsGames")
        data class Games(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val appearances: Int?,
            val lineups: Int?,
            val minutes: Int?,
            val number: Int?,
            val position: String?,
            val rating: String?,
            val captain: Boolean?
        )
        @Entity(tableName = "PlayerStatsGoals")
        data class Goals(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val total: Int?,
            val conceded: Int?,
            val assists: Int?,
            val saves: Int?
        )
        @Entity(tableName = "PlayerStatsPasses")
        data class Passes(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val total: Int?,
            val key: Int?,
            val accuracy: Int?,
        )
        @Entity(tableName = "PlayerStatsTackles")
        data class Tackles(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val total: Int?,
            val blocks: Int?,
            val interceptions: Int?,
        )
        @Entity(tableName = "PlayerStatsDuels")
        data class Duels(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val total: Int?,
            val won: Int?,
        )
        @Entity(tableName = "PlayerStatsDribbles")
        data class Dribbles(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val attempts: Int?,
            val success: Int?,
            val past: Int?,
        )
        @Entity(tableName = "PlayerStatsFouls")
        data class Fouls(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val drawn: Int?,
            val committed: Int?,
        )
        @Entity(tableName = "PlayerStatsCards")
        data class Cards(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val yellow: Int?,
            val yellowred: Int?,
            val red: Int?,
        )
        @Entity(tableName = "PlayerStatsPenalty")
        data class Penalty(
            @PrimaryKey (autoGenerate = true)
            val id: Int = 0,
            val won: Int?,
            val commited: Int?,
            val scored: Int?,
            val missed: Int?,
            val saved: Int?,
        )
    }
}
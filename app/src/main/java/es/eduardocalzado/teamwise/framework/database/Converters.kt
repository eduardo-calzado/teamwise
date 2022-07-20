package es.eduardocalzado.teamwise.framework.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
class Converters() {

    // #MARK: Player.Stats
    @TypeConverter
    fun toPlayerStatsJson(statsLeague: List<Player.Stats>) : String {
        return Gson().toJson(statsLeague)
    }

    @TypeConverter
    fun fromPlayerStatsJson(json: String): List<Player.Stats> {
        val listType: Type = object : TypeToken<List<Player.Stats?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    // #MARK: Player.Stats.Team
    @TypeConverter
    fun toPlayerStatsTeamJson(string: Player.Stats.Team) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsTeamJson(json: String): Player.Stats.Team {
        return Gson().fromJson(json, Player.Stats.Team::class.java)
    }

    // #MARK: Player.Stats.League
    @TypeConverter
    fun toPlayerStatsLeagueJson(string: Player.Stats.League) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsLeagueJson(json: String): Player.Stats.League {
        return Gson().fromJson(json, Player.Stats.League::class.java)
    }

    // #MARK: Player.Stats.Games
    @TypeConverter
    fun toPlayerStatsGamesJson(string: Player.Stats.Games) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsGamesJson(json: String): Player.Stats.Games {
        return Gson().fromJson(json, Player.Stats.Games::class.java)
    }

    // #MARK: Player.Stats.Goals
    @TypeConverter
    fun toPlayerStatsGoalsJson(string: Player.Stats.Goals) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsGoalsJson(json: String): Player.Stats.Goals {
        return Gson().fromJson(json, Player.Stats.Goals::class.java)
    }

    // #MARK: Player.Stats.Passes
    @TypeConverter
    fun toPlayerStatsPassesJson(string: Player.Stats.Passes) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsPassesJson(json: String): Player.Stats.Passes {
        return Gson().fromJson(json, Player.Stats.Passes::class.java)
    }

    // #MARK: Player.Stats.Tackles
    @TypeConverter
    fun toPlayerStatsTacklesJson(string: Player.Stats.Tackles) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsTacklesJson(json: String): Player.Stats.Tackles {
        return Gson().fromJson(json, Player.Stats.Tackles::class.java)
    }

    // #MARK: Player.Stats.Duels
    @TypeConverter
    fun toPlayerStatsDuelsJson(string: Player.Stats.Duels) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsDuelsJson(json: String): Player.Stats.Duels {
        return Gson().fromJson(json, Player.Stats.Duels::class.java)
    }

    // #MARK: Player.Stats.Dribbles
    @TypeConverter
    fun toPlayerStatsDribblesJson(string: Player.Stats.Dribbles) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsDribblesJson(json: String): Player.Stats.Dribbles {
        return Gson().fromJson(json, Player.Stats.Dribbles::class.java)
    }

    // #MARK: Player.Stats.Fouls
    @TypeConverter
    fun toPlayerStatsFoulsJson(string: Player.Stats.Fouls) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsFoulsJson(json: String): Player.Stats.Fouls {
        return Gson().fromJson(json, Player.Stats.Fouls::class.java)
    }

    // #MARK: Player.Stats.Cards
    @TypeConverter
    fun toPlayerStatsCardsJson(string: Player.Stats.Cards) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsCardsJson(json: String): Player.Stats.Cards {
        return Gson().fromJson(json, Player.Stats.Cards::class.java)
    }

    // #MARK: Player.Stats.Penalty
    @TypeConverter
    fun toPlayerStatsPenaltyJson(string: Player.Stats.Penalty) : String {
        return Gson().toJson(string)
    }

    @TypeConverter
    fun fromPlayerStatsPenaltyJson(json: String): Player.Stats.Penalty {
        return Gson().fromJson(json, Player.Stats.Penalty::class.java)
    }
}
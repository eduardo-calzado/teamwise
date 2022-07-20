package es.eduardocalzado.teamwise.framework.database

import androidx.room.PrimaryKey
import es.eduardocalzado.teamwise.data.datasource.PlayerLocalDataSource
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.framework.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import es.eduardocalzado.teamwise.framework.database.Player as PlayerDB

class PlayerRoomDataSource @Inject constructor(private val playerDao: PlayerDao) : PlayerLocalDataSource {
    // -- get all the players
    override val players: Flow<List<Player>> = playerDao.getAll().map { it.toDomainModel() }
    // -- if there are no players
    override suspend fun isEmpty() : Boolean = playerDao.playersCount() == 0
    // -- find the player by id
    override fun findById(id: Int): Flow<Player> = playerDao.findById(id).map { it.toDomainModel() }
    // -- find the team players
    override fun findByTeam(team: Int): Flow<List<Player>> = playerDao.findByTeam(team).map { it.toDomainModel() }
    // -- search the team
    override fun searchPlayers(query: String): Flow<List<Player>> = playerDao.searchPlayers(query).map { it.toDomainModel() }
    // -- filter the team players
    override suspend fun filterByTeam(team: Int): List<Player> = playerDao.filterByTeam(team).map { it.toDomainModel() }
    // -- save the player
    override suspend fun save (players: List<Player>) = tryCall {
        playerDao.insertPlayers(players.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
    // -- delete players
    override suspend fun deletePlayers() = playerDao.deletePlayers()
}

// #MARK: PlayerDB.toDomainModel
@JvmName("toDomainModelPlayerDB")
private fun List<PlayerDB>.toDomainModel(): List<Player> = map { it.toDomainModel() }
private fun PlayerDB.toDomainModel(): Player =
    Player(
        id,
        name,
        firstName,
        lastName,
        age,
        nationality,
        height,
        weight,
        injured,
        photo,
        team,
        statistics.toDomainModel()
    )

// #MARK: Player.toDomainModel
@JvmName("toDomainModelPlayer")
private fun List<Player>.fromDomainModel(): List<PlayerDB> = map { it.fromDomainModel() }
private fun Player.fromDomainModel(): PlayerDB =
    PlayerDB (
        id,
        name,
        firstName,
        lastName,
        age,
        nationality,
        height,
        weight,
        injured,
        photo,
        team,
        statistics.fromDomainModel()
    )

// #MARK: PlayerDB.Stats.toDomainModel
@JvmName("toDomainModelPlayerDBStats")
private fun List<PlayerDB.Stats>.toDomainModel(): List<Player.Stats> = map { it.toDomainModel() }
private fun PlayerDB.Stats.toDomainModel(): Player.Stats =
    Player.Stats (
        team = team.toDomainModel(),
        league = league.toDomainModel(),
        games = games.toDomainModel(),
        goals = goals.toDomainModel(),
        passes = passes.toDomainModel(),
        tackles = tackles.toDomainModel(),
        duels = duels.toDomainModel(),
        dribbles = dribbles.toDomainModel(),
        fouls = fouls.toDomainModel(),
        cards = cards.toDomainModel(),
        penalty = penalty.toDomainModel(),
    )

// #MARK: PlayerDB.Stats.Team.toDomainModel
@JvmName("toDomainModelPlayerDBStatsTeam")
private fun PlayerDB.Stats.Team.toDomainModel(): Player.Stats.Team =
    Player.Stats.Team (
        id = id,
        name = name,
        logo = logo,
    )

// #MARK: PlayerDB.Stats.Leagues.toDomainModel
@JvmName("toDomainModelPlayerDBStatsLeagues")
private fun PlayerDB.Stats.League.toDomainModel(): Player.Stats.League =
    Player.Stats.League (
        id = id,
        name = name,
        country = country,
        logo = logo,
        flag = flag,
        season = season
    )

// #MARK: PlayerDB.Stats.Games.toDomainModel
@JvmName("toDomainModelPlayerDBStatsGames")
private fun PlayerDB.Stats.Games.toDomainModel(): Player.Stats.Games =
    Player.Stats.Games (
        appearances = appearances,
        lineups = lineups,
        minutes = minutes,
        number = number,
        position = position,
        rating = rating,
        captain = captain,
    )

// #MARK: PlayerDB.Stats.Goals.toDomainModel
@JvmName("toDomainModelPlayerDBStatsGoals")
private fun PlayerDB.Stats.Goals.toDomainModel(): Player.Stats.Goals =
    Player.Stats.Goals (
        total = total,
        conceded = conceded,
        assists = assists,
        saves = saves
    )

// #MARK: PlayerDB.Stats.Passes.toDomainModel
@JvmName("toDomainModelPlayerDBStatsPasses")
private fun PlayerDB.Stats.Passes.toDomainModel(): Player.Stats.Passes =
    Player.Stats.Passes (
        total = total,
        key = key,
        accuracy = accuracy,
    )

// #MARK: PlayerDB.Stats.Tackles.toDomainModel
@JvmName("toDomainModelPlayerDBStatsTackles")
private fun PlayerDB.Stats.Tackles.toDomainModel(): Player.Stats.Tackles =
    Player.Stats.Tackles (
        total = total,
        blocks = blocks,
        interceptions = interceptions,
    )

// #MARK: PlayerDB.Stats.Duels.toDomainModel
@JvmName("toDomainModelPlayerDBStatsDuels")
private fun PlayerDB.Stats.Duels.toDomainModel(): Player.Stats.Duels =
    Player.Stats.Duels (
        total = total,
        won = won,
    )

// #MARK: PlayerDB.Stats.Dribbles.toDomainModel
@JvmName("toDomainModelPlayerDBStatsDribbles")
private fun PlayerDB.Stats.Dribbles.toDomainModel(): Player.Stats.Dribbles =
    Player.Stats.Dribbles (
        attempts = attempts,
        success = success,
        past = past,
    )

// #MARK: PlayerDB.Stats.Fouls.toDomainModel
@JvmName("toDomainModelPlayerDBStatsFouls")
private fun PlayerDB.Stats.Fouls.toDomainModel(): Player.Stats.Fouls =
    Player.Stats.Fouls (
        drawn = drawn,
        committed = committed,
    )

// #MARK: PlayerDB.Stats.Cards.toDomainModel
@JvmName("toDomainModelPlayerDBStatsCards")
private fun PlayerDB.Stats.Cards.toDomainModel(): Player.Stats.Cards =
    Player.Stats.Cards (
        yellow = yellow,
        yellowred = yellowred,
        red = red,
    )

// #MARK: PlayerDB.Stats.Penalty.toDomainModel
@JvmName("toDomainModelPlayerDBStatsPenalty")
private fun PlayerDB.Stats.Penalty.toDomainModel(): Player.Stats.Penalty =
    Player.Stats.Penalty (
        won = won,
        commited = commited,
        scored = scored,
        missed = missed,
        saved = saved,
    )

// #MARK: Player.Stats.fromDomainModel
@JvmName("toDomainModelPlayerStats")
private fun List<Player.Stats>.fromDomainModel(): List<PlayerDB.Stats> = map { it.fromDomainModel() }
private fun Player.Stats.fromDomainModel(): PlayerDB.Stats =
    PlayerDB.Stats (
        team = team.fromDomainModel(),
        league = league.fromDomainModel(),
        games = games.fromDomainModel(),
        goals = goals.fromDomainModel(),
        passes = passes.fromDomainModel(),
        tackles = tackles.fromDomainModel(),
        duels = duels.fromDomainModel(),
        dribbles = dribbles.fromDomainModel(),
        fouls = fouls.fromDomainModel(),
        cards = cards.fromDomainModel(),
        penalty = penalty.fromDomainModel(),
    )

// #MARK: Player.Stats.Team.fromDomainModel
@JvmName("toDomainModelPlayerStatsTeam")
private fun Player.Stats.Team.fromDomainModel(): PlayerDB.Stats.Team =
    PlayerDB.Stats.Team (
        id = id,
        name = name,
        logo = logo,
    )

// #MARK: Player.Stats.League.fromDomainModel
@JvmName("toDomainModelPlayerStatsLeagues")
private fun Player.Stats.League.fromDomainModel(): PlayerDB.Stats.League =
    PlayerDB.Stats.League (
        id = id,
        name = name,
        country = country,
        logo = logo,
        flag = flag,
        season = season
    )

// #MARK: Player.Stats.Games.fromDomainModel
@JvmName("toDomainModelPlayerStatsGames")
private fun Player.Stats.Games.fromDomainModel(): PlayerDB.Stats.Games =
    PlayerDB.Stats.Games (
        appearances = appearances,
        lineups = lineups,
        minutes = minutes,
        number = number,
        position = position,
        rating = rating,
        captain = captain,
    )

// #MARK: Player.Stats.Goals.fromDomainModel
@JvmName("toDomainModelPlayerStatsGoals")
private fun Player.Stats.Goals.fromDomainModel(): PlayerDB.Stats.Goals =
    PlayerDB.Stats.Goals (
        total = total,
        conceded = conceded,
        assists = assists,
        saves = saves
    )

// #MARK: Player.Stats.Passes.fromDomainModel
@JvmName("toDomainModelPlayerStatsPasses")
private fun Player.Stats.Passes.fromDomainModel(): PlayerDB.Stats.Passes =
    PlayerDB.Stats.Passes (
        total = total,
        key = key,
        accuracy = accuracy,
    )

// #MARK: Player.Stats.Tackles.fromDomainModel
@JvmName("toDomainModelPlayerStatsTackles")
private fun Player.Stats.Tackles.fromDomainModel(): PlayerDB.Stats.Tackles =
    PlayerDB.Stats.Tackles (
        total = total,
        blocks = blocks,
        interceptions = interceptions,
    )

// #MARK: Player.Stats.Duels.fromDomainModel
@JvmName("toDomainModelPlayerStatsDuels")
private fun Player.Stats.Duels.fromDomainModel(): PlayerDB.Stats.Duels =
    PlayerDB.Stats.Duels (
        total = total,
        won = won,
    )

// #MARK: Player.Stats.Dribbles.fromDomainModel
@JvmName("toDomainModelPlayerStatsDribbles")
private fun Player.Stats.Dribbles.fromDomainModel(): PlayerDB.Stats.Dribbles =
    PlayerDB.Stats.Dribbles (
        attempts = attempts,
        success = success,
        past = past,
    )

// #MARK: Player.Stats.Fouls.fromDomainModel
@JvmName("toDomainModelPlayerStatsFouls")
private fun Player.Stats.Fouls.fromDomainModel(): PlayerDB.Stats.Fouls =
    PlayerDB.Stats.Fouls (
        drawn = drawn,
        committed = committed,
    )

// #MARK: Player.Stats.Cards.fromDomainModel
@JvmName("toDomainModelPlayerStatsCards")
private fun Player.Stats.Cards.fromDomainModel(): PlayerDB.Stats.Cards =
    PlayerDB.Stats.Cards (
        yellow = yellow,
        yellowred = yellowred,
        red = red,
    )

// #MARK: Player.Stats.Penalty.fromDomainModel
@JvmName("toDomainModelPlayerStatsPenalty")
private fun Player.Stats.Penalty.fromDomainModel(): PlayerDB.Stats.Penalty =
    PlayerDB.Stats.Penalty (
        won = won,
        commited = commited,
        scored = scored,
        missed = missed,
        saved = saved,
    )
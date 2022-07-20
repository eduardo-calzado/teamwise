package es.eduardocalzado.teamwise.framework.server

import arrow.core.Either
import es.eduardocalzado.teamwise.data.datasource.PlayerRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.framework.tryCall
import javax.inject.Inject

class PlayerServerDataSource @Inject constructor () : PlayerRemoteDataSource {
    /**
     * getPlayersByTeam. Get the player list for a specific team
     */
    override suspend fun getPlayersByTeam(team : Int, season : Int) : Either<Error, List<Player>> = tryCall {
        RemoteConnection
            .service
            .getPlayersByTeam(team, season)
            .players
            .toDomainModel()
    }
}

// #MARK: RemotePlayers.Players.toDomainModel
@JvmName("toDomainModelRemotePlayers")
private fun List<RemotePlayers.Players>.toDomainModel(): List<Player> = map { it.toDomainModel() }
private fun RemotePlayers.Players.toDomainModel(): Player =
    Player(
        id = player.id,
        name = player.name,
        firstName = player.firstName,
        lastName = player.lastName,
        age = player.age,
        nationality = player.nationality,
        height = player.height,
        weight = player.weight,
        injured = player.injured,
        photo = player.photo,
        team = -1,
        statistics = statistics.toDomainModel(),
    )

// #MARK: RemotePlayers.Players.Stats.toDomainModel
@JvmName("toDomainModelRemotePlayerInfoData")
private fun List<RemotePlayers.Players.Stats>.toDomainModel(): List<Player.Stats> = map { it.toDomainModel() }
private fun RemotePlayers.Players.Stats.toDomainModel(): Player.Stats =
    Player.Stats(
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

// #MARK: RemotePlayers.Players.Stats.Team.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsTeam")
private fun RemotePlayers.Players.Stats.Team.toDomainModel(): Player.Stats.Team =
    Player.Stats.Team(
        id = id,
        name = name,
        logo = logo,
    )

// #MARK: RemotePlayers.Players.Stats.Team.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsLeague")
private fun RemotePlayers.Players.Stats.League.toDomainModel(): Player.Stats.League =
    Player.Stats.League(
        id = id,
        name = name,
        country = country,
        logo = logo,
        flag = flag,
        season = season
    )

// #MARK: RemotePlayers.Players.Stats.Games.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsGames")
private fun RemotePlayers.Players.Stats.Games.toDomainModel(): Player.Stats.Games =
    Player.Stats.Games(
        appearances = appearences,
        lineups = lineups,
        minutes = minutes,
        number = number,
        position = position,
        rating = rating,
        captain = captain,
    )

// #MARK: RemotePlayers.Players.Stats.Goals.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsGoals")
private fun RemotePlayers.Players.Stats.Goals.toDomainModel(): Player.Stats.Goals =
    Player.Stats.Goals (
        total = total,
        conceded = conceded,
        assists = assists,
        saves = saves
    )

// #MARK: RemotePlayers.Players.Stats.Passes.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsPasses")
private fun RemotePlayers.Players.Stats.Passes.toDomainModel(): Player.Stats.Passes =
    Player.Stats.Passes (
        total = total,
        key = key,
        accuracy = accuracy,
    )

// #MARK: RemotePlayers.Players.Stats.Tackles.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsTackles")
private fun RemotePlayers.Players.Stats.Tackles.toDomainModel(): Player.Stats.Tackles =
    Player.Stats.Tackles (
        total = total,
        blocks = blocks,
        interceptions = interceptions,
    )

// #MARK: RemotePlayers.Players.Stats.Duels.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsDuels")
private fun RemotePlayers.Players.Stats.Duels.toDomainModel(): Player.Stats.Duels =
    Player.Stats.Duels (
        total = total,
        won = won,
    )

// #MARK: RemotePlayers.Players.Stats.Dribbles.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsDribbles")
private fun RemotePlayers.Players.Stats.Dribbles.toDomainModel(): Player.Stats.Dribbles =
    Player.Stats.Dribbles (
        attempts = attempts,
        success = success,
        past = past,
    )

// #MARK: RemotePlayers.Players.Stats.Fouls.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsFouls")
private fun RemotePlayers.Players.Stats.Fouls.toDomainModel(): Player.Stats.Fouls =
    Player.Stats.Fouls (
        drawn = drawn,
        committed = committed,
    )

// #MARK: RemotePlayers.Players.Stats.Cards.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsCards")
private fun RemotePlayers.Players.Stats.Cards.toDomainModel(): Player.Stats.Cards =
    Player.Stats.Cards (
        yellow = yellow,
        yellowred = yellowred,
        red = red,
    )

// #MARK: RemotePlayers.Players.Stats.Penalty.toDomainModel
@JvmName("toDomainModelRemotePlayersStatsPenalty")
private fun RemotePlayers.Players.Stats.Penalty.toDomainModel(): Player.Stats.Penalty =
    Player.Stats.Penalty (
        won = won,
        commited = commited,
        scored = scored,
        missed = missed,
        saved = saved,
    )
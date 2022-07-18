package es.eduardocalzado.teamwise.framework.database

import es.eduardocalzado.teamwise.data.datasource.PlayerLocalDataSource
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.Team
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

// #MARK: toDomainModel
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
    )

// #MARK: fromDomainModel
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
    )
package es.eduardocalzado.teamwise.data

import es.eduardocalzado.teamwise.data.datasource.PlayerLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.PlayerRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    private val localDataSource: PlayerLocalDataSource,
    private val remoteDataSource: PlayerRemoteDataSource,
) {
    val players = localDataSource.players

    fun findById(id: Int) : Flow<Player> = localDataSource.findById(id)

    fun findByTeam(team: Int) : Flow<List<Player>> = localDataSource.findByTeam(team)

    fun searchPlayers(query: String, teamId: Int) = localDataSource.searchPlayers(query, teamId)

    suspend fun requestPlayersByTeam(team: Int, season: Int): Error? {
        if (localDataSource.filterByTeam(team).isNullOrEmpty()) {
            val playersData = remoteDataSource.getPlayersByTeam(team, season)
            playersData.fold(ifLeft = { return it }) { players ->
                players.map { it.team = team }
                localDataSource.save(players)
            }
        }
        return null
    }
}
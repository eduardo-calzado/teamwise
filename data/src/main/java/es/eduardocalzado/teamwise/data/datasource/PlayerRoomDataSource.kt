package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow

interface PlayerLocalDataSource {
    val players: Flow<List<Player>>

    suspend fun isEmpty() : Boolean
    fun findById(id: Int): Flow<Player>
    fun findByTeam(team: Int): Flow<List<Player>>
    suspend fun filterByTeam(team: Int): List<Player>
    suspend fun save (players: List<Player>): Error?
    suspend fun deletePlayers()
    fun searchPlayers(query: String, teamId: Int): Flow<List<Player>>
}
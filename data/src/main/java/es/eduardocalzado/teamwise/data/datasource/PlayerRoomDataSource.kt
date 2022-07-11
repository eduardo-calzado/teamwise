package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import kotlinx.coroutines.flow.Flow

interface PlayerLocalDataSource {
    val players: Flow<List<Player>>

    suspend fun isEmpty() : Boolean
    fun findById(id: Int): Flow<Player>
    suspend fun save (players: List<Player>): Error?
    suspend fun deletePlayers()
}
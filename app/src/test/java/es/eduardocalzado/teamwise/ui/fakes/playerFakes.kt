package es.eduardocalzado.teamwise.ui.fakes

import arrow.core.Either
import arrow.core.right
import es.eduardocalzado.teamwise.data.datasource.PlayerLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.PlayerRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.samplePlayers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

val defaultFakePlayers = samplePlayers

class FakePlayerLocalDataSource : PlayerLocalDataSource {
    val inMemoryPlayers = MutableStateFlow<List<Player>>(emptyList())
    override val players = inMemoryPlayers
    private lateinit var findPlayerFlow: MutableStateFlow<Player>

    override suspend fun isEmpty() = players.value.isEmpty()

    override fun findById(id: Int): Flow<Player> {
        findPlayerFlow = MutableStateFlow(inMemoryPlayers.value.first { it.id == id})
        return findPlayerFlow
    }

    override fun findByTeam(team: Int): Flow<List<Player>> {
        inMemoryPlayers.value = inMemoryPlayers.value.filter { it.team == team }
        return inMemoryPlayers
    }

    override suspend fun filterByTeam(team: Int): List<Player> {
        return MutableStateFlow(inMemoryPlayers.value.filter { it.team == team }).value
    }

    override suspend fun save(players: List<Player>): Error? {
        inMemoryPlayers.value = players
        return null
    }

    override suspend fun deletePlayers() {
        inMemoryPlayers.value = emptyList()
    }

    override fun searchPlayers(search: String, teamId: Int): Flow<List<Player>> {
        return flowOf(inMemoryPlayers.value.filter { it.name.contains(search) && it.team == teamId })
    }
}

class FakePlayerRemoteDataSource : PlayerRemoteDataSource {
    var players = defaultFakePlayers

    override suspend fun getPlayersByTeam(team: Int, season: Int): Either<Error, List<Player>> {
        return players.filter { it.team == team }.right()
    }
}
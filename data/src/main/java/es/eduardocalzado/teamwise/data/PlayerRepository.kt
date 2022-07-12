package es.eduardocalzado.teamwise.data

import es.eduardocalzado.teamwise.data.datasource.PlayerLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.PlayerRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    private val localDataSource: PlayerLocalDataSource,
    private val remoteDataSource: PlayerRemoteDataSource,
) {
    val players = localDataSource.players

    fun findById(id: Int) : Flow<Player> = localDataSource.findById(id)
    
    suspend fun requestPlayersByTeam(team: Int, season: Int): Error? {
        //if (localDataSource.isEmpty()) {
            val playersData = remoteDataSource.getPlayersByTeam(team, season)
            playersData.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        //}
        return null
    }
}
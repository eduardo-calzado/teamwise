package es.eduardocalzado.teamwise.framework.server

import arrow.core.Either
import es.eduardocalzado.teamwise.data.datasource.PlayerRemoteDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.TeamStats
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

// #MARK: RemoteTeam.toDomainModel
@JvmName("toDomainModelRemotePlayer")
private fun List<RemotePlayer>.toDomainModel(): List<Player> = map { it.toDomainModel() }
private fun RemotePlayer.toDomainModel(): Player =
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
        photo = player.photo
    )
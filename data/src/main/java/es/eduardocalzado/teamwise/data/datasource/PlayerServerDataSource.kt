package es.eduardocalzado.teamwise.data.datasource

import arrow.core.Either
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player

interface PlayerRemoteDataSource {
    suspend fun getPlayersByTeam(team: Int, season: Int) : Either<Error, List<Player>>
}
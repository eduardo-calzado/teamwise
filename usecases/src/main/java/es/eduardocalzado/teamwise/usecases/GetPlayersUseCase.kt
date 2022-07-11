package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor (private val playerRepository: PlayerRepository) {
    operator fun invoke(): Flow<List<Player>> = playerRepository.players
}
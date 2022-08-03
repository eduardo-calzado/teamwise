package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import javax.inject.Inject

class SearchPlayersUseCase @Inject constructor (private val repository: PlayerRepository) {
    operator fun invoke (query: String, teamId: Int) = repository.searchPlayers(query, teamId)
}
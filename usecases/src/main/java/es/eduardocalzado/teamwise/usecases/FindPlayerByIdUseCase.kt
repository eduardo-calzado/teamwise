package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import javax.inject.Inject

class FindPlayerByIdUseCase @Inject constructor (private val repository: PlayerRepository) {
    operator fun invoke (id: Int) = repository.findById(id)
}
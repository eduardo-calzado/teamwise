package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import javax.inject.Inject

class FindTeamUseCase @Inject constructor (private val repository: TeamRepository) {
    operator fun invoke (id: Int) = repository.findById(id)
}
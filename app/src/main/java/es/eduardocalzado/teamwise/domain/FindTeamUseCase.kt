package es.eduardocalzado.teamwise.domain

import es.eduardocalzado.teamwise.data.network.TeamRepository

class FindTeamUseCase (private val repository: TeamRepository) {
    operator fun invoke (id: Int) = repository.findById(id)
}
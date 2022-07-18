package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import javax.inject.Inject

class SearchTeamsUseCase @Inject constructor (private val repository: TeamRepository) {
    operator fun invoke (query: String) = repository.searchTeams(query)
}
package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegionRepositoryUseCase @Inject constructor (private val teamRepository: TeamRepository) {
    suspend operator fun invoke(): String = teamRepository.getRegionRepository()
}
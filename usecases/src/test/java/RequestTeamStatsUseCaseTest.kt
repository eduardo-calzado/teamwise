import arrow.core.right
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.RequestTeamStatsUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamsByRegionUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestTeamStatsUseCaseTest {
    @Test
    fun `Invoke call teams stats repository`() : Unit = runBlocking {
        // GIVEN
        val teamRepository = mock<TeamRepository>()
        val requestTeamStatsUseCase = RequestTeamStatsUseCase(teamRepository)
        // WHEN
        requestTeamStatsUseCase(-1, -1, sampleTeam.id)
        // THEN
        verify(teamRepository).requestTeamStats(-1, -1, sampleTeam.id)
    }
}
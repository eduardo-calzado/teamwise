import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.usecases.RequestTeamsByRegionUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestTeamsByRegionUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val teamRepository = mock<TeamRepository>()
        val requestTeamsUseCase = RequestTeamsByRegionUseCase(teamRepository)
        // WHEN
        requestTeamsUseCase()
        // THEN
        verify(teamRepository).requestTeamsByRegion()
    }
}
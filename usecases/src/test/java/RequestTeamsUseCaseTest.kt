import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.usecases.RequestTeamsByRegionUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestTeamsUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val teamRepository = mock<TeamRepository>()
        val requestTeamsUseCase = RequestTeamsUseCase(teamRepository)
        // WHEN
        requestTeamsUseCase("England", -1, -1)
        // THEN
        verify(teamRepository).requestTeams("England", -1, -1)
    }
}
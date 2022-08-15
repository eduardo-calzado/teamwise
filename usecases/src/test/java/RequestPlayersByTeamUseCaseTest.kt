import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.usecases.RequestPlayersByTeamUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestPlayersByTeamUseCaseTest {
    @Test
    fun `Invoke call players repository by team`() : Unit = runBlocking {
        // GIVEN
        val playerRepository = mock<PlayerRepository>()
        val requestPlayersByTeamUseCase = RequestPlayersByTeamUseCase(playerRepository)
        // WHEN
        requestPlayersByTeamUseCase(1, 2021)
        // THEN
        verify(playerRepository).requestPlayersByTeam(1, 2021)
    }
}
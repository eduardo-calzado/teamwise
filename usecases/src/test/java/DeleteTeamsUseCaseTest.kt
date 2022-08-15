import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.usecases.DeleteTeamsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTeamsUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val teamRepository = mock<TeamRepository>()
        val deleteTeamsUseCase = DeleteTeamsUseCase(teamRepository)
        // THEN
        deleteTeamsUseCase()
        // WHEN
        verify(teamRepository).deleteTeams()
    }
}
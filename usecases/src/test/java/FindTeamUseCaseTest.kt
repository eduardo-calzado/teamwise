import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindTeamUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val team = flowOf(sampleTeam.copy())
        val findTeamUseCase = FindTeamUseCase(mock() {
            on { findById(id = 1) } doReturn (team)
        })
        // WHEN
        val result = findTeamUseCase(1)
        // THEN
        assertEquals(team, result)
    }
}
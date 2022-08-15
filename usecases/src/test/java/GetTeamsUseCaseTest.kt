import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.GetTeamsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetTeamsUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val teams = flowOf(listOf(sampleTeam.copy(id=1)))
        val getTeamsUseCase = GetTeamsUseCase(mock() {
            on { teams } doReturn (teams)
        })
        // WHEN
        val result = getTeamsUseCase()
        // THEN
        assertEquals(teams, result)
    }
}
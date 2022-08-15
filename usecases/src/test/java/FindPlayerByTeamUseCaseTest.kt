import es.eduardocalzado.teamwise.samplePlayer
import es.eduardocalzado.teamwise.samplePlayers
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.FindPlayerByIdUseCase
import es.eduardocalzado.teamwise.usecases.FindPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindPlayerByTeamUseCaseTest {
    @Test
    fun `Invoke call players repository by team`() : Unit = runBlocking {
        // GIVEN
        val players = flowOf(samplePlayers)
        val findPlayersByTeamUseCase = FindPlayersByTeamUseCase(mock() {
            on { findByTeam(team = 1) } doReturn (players)
        })
        // WHEN
        val result = findPlayersByTeamUseCase(1)
        // THEN
        assertEquals(players, result)
    }
}
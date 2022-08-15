import es.eduardocalzado.teamwise.samplePlayer
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.FindPlayerByIdUseCase
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindPlayersByIdUseCaseTest {
    @Test
    fun `Invoke call players repository by id`() : Unit = runBlocking {
        // GIVEN
        val player = flowOf(samplePlayer.copy())
        val findPlayerByIdUseCase = FindPlayerByIdUseCase(mock() {
            on { findById(id = 1) } doReturn (player)
        })
        // WHEN
        val result = findPlayerByIdUseCase(1)
        // THEN
        assertEquals(player, result)
    }
}
import es.eduardocalzado.teamwise.samplePlayer
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.GetPlayersUseCase
import es.eduardocalzado.teamwise.usecases.GetTeamsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetPlayersUseCaseTest {
    @Test
    fun `Invoke call players repository`() : Unit = runBlocking {
        // GIVEN
        val players = flowOf(listOf(samplePlayer.copy(id=1)))
        val getPlayersUseCase = GetPlayersUseCase(mock() {
            on { mock.players } doReturn (players)
        })
        // WHEN
        val result = getPlayersUseCase()
        // THEN
        assertEquals(players, result)
    }
}
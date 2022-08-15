import es.eduardocalzado.teamwise.samplePlayer
import es.eduardocalzado.teamwise.samplePlayers
import es.eduardocalzado.teamwise.sampleTeams
import es.eduardocalzado.teamwise.usecases.SearchPlayersUseCase
import es.eduardocalzado.teamwise.usecases.SearchTeamsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class SearchPlayersUseCaseTest {
    @Test
    fun `Invoke call search players use case`() : Unit = runBlocking {
        // GIVEN
        val playersResult = flowOf(listOf(samplePlayer))
        val searchPlayersUseCase = SearchPlayersUseCase(mock() {
            on { searchPlayers(query = "Neymar", teamId = 1) } doReturn (playersResult)
        })
        // WHEN
        val result = searchPlayersUseCase("Neymar", 1)
        // THEN
        Assert.assertEquals(playersResult, result)
    }
}
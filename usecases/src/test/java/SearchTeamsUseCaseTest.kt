import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.sampleTeams
import es.eduardocalzado.teamwise.usecases.SearchTeamsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class SearchTeamsUseCaseTest {
    @Test
    fun `Invoke call search teams use case`() : Unit = runBlocking {
        // GIVEN
        val teamResult = flowOf(listOf(sampleTeam))
        val searchTeamsUseCase = SearchTeamsUseCase(mock() {
            on { searchTeams(query = "Racing de Santander") } doReturn (teamResult)
        })
        // WHEN
        val result = searchTeamsUseCase("Racing de Santander")
        // THEN
        Assert.assertEquals(teamResult, result)
    }
}
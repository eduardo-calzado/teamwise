package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.sampleTeam
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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
package es.eduardocalzado.teamwise.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindTeamUseCaseTest {

    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val team = flowOf(sampleTeam.copy(id=1))
        val findTeamUseCase = FindTeamUseCase(mock() {
            on { findById(1) } doReturn (team)
        })
        // WHEN
        val result = findTeamUseCase(1)
        // THEN
        assertEquals(team, result)
    }
}
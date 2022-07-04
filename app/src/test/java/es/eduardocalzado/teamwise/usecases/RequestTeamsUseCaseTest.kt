package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestTeamsUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val teamRepository = mock<TeamRepository>()
        val requestTeamsUseCase = RequestTeamsUseCase(teamRepository)
        // WHEN
        requestTeamsUseCase()
        // THEN
        verify(teamRepository).requestTeams()
    }
}
package es.eduardocalzado.teamwise.usecases

import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.sampleTeam
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchTeamFavoriteUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        val team = sampleTeam.copy(id = 1)
        val teamRepository = mock<TeamRepository>()
        val switchTeamFavoriteUseCase = SwitchTeamFavoriteUseCase(teamRepository)

        switchTeamFavoriteUseCase(team)

        verify(teamRepository).switchFavorite(team)
    }
}
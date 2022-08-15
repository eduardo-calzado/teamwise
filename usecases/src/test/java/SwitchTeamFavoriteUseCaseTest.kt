import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.sampleTeam2
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchTeamFavoriteUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val team = sampleTeam2
        val teamRepository = mock<TeamRepository>()
        val switchTeamFavoriteUseCase = SwitchTeamFavoriteUseCase(teamRepository)
        // THEN
        switchTeamFavoriteUseCase(team)
        // WHEN
        verify(teamRepository).switchFavorite(team)
    }
}
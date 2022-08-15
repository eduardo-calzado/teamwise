package es.eduardocalzado.teamwise.ui.detail.stats

import app.cash.turbine.test
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.ui.buildRepositoryWith
import es.eduardocalzado.teamwise.ui.detail.stats.DetailViewModel.UiState
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamStatsUseCase
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val vm = buildViewModelWith(
            teamId = 2,
            localData = listOf(sampleTeam.copy(1), sampleTeam.copy(2)),
            remoteData = emptyList()
        )
        vm.onFavoriteClicked()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(teamData = sampleTeam.copy(2, favorite = true)), awaitItem())
        }
    }

    private fun buildViewModelWith(
        teamId: Int,
        leagueId: Int = -1,
        seasonId: Int = -1,
        localData: List<Team> = emptyList(),
        remoteData: List<Team> = emptyList()
    ): DetailViewModel {
        val teamRepository = buildRepositoryWith(localData, remoteData)
        val findTeamUseCase = FindTeamUseCase(teamRepository)
        val requestTeamStatsUseCase = RequestTeamStatsUseCase(teamRepository)
        val switchTeamFavoriteUseCase = SwitchTeamFavoriteUseCase(teamRepository)
        return DetailViewModel(teamId, leagueId, seasonId, findTeamUseCase, requestTeamStatsUseCase, switchTeamFavoriteUseCase)
    }
}